package com.perficient.search.source;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.lookup.StringLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.collection.PageIterator;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.core.Document;
import com.filenet.api.property.Property;
import com.perficient.search.content.SourceContent;
import com.perficient.search.content.SourceContentBatch;
import com.perficient.search.content.SourceInterface;
import com.perficient.search.source.filenet.FilenetConnection;
import com.perficient.search.source.filenet.FilenetContext;
import com.perficient.search.source.filenet.MissingPropertyException;

public class FilenetSource implements SourceInterface {
   private static final Logger log = LoggerFactory.getLogger(FilenetSource.class);

   private Map<String, Object> properties;
   private FilenetConnection   connection;

   private String SQL = "select ${ID} from Document where ${VERSION_STATUS}=1 and ${CONTENT_SIZE} is not null and (not Document.This insubfolder '/Trash') order by ${ID}";

   @Override
   public void init(Map<String, Object> properties) throws Exception {
      this.properties = properties;
      final String ceurl    = getString("ceurl");
      final String username = getString("username");
      final String password = getString("password");
      final String osname   = getString("objectstore");

      this.connection = new FilenetConnection(ceurl, username, password, osname);
   }

   @Override
   public SourceContentBatch getContent(byte[] checkpoint) throws Exception {
      final SourceContentBatch batch = new SourceContentBatch();

      try (final FilenetContext context = this.connection.getContext()) {
         final PageIterator pit = ArrayUtils.isEmpty(checkpoint) ? startQuery() : resumeQuery(checkpoint);
         if (pit.nextPage()) {
            log.debug("There are more results.");
            batch.setHasMore(true);
         } else {
            log.info("Full traversal complete.");
            batch.setHasMore(false);
         }

         for (Object o : pit.getCurrentPage()) {
            Document doc = (Document) o;

            final SourceContent content = new SourceContent();

            content.setId(doc.get_Id().toString());

            for (Property property : doc.getProperties().toArray()) {
               content.getMetadata().put(property.getPropertyName(), property.getObjectValue());
            }

            batch.addContent(content);
         }

         batch.setCheckpoint(pit.getNextPageCheckpoint());
      }

      log.debug("getContent returns:\n{}", batch.toString());

      return batch;
   }

   private PageIterator startQuery() throws MissingPropertyException {
      final Integer batchSize = MapUtils.getInteger(this.properties, "batchSize", 10);
      final String  sql       = processVariables(getString("sql"));

      log.debug("Query sql=[ {} ]", sql);

      IndependentObjectSet results = this.connection.query(batchSize, sql);
      return results.pageIterator();
   }

   private PageIterator resumeQuery(byte[] checkpoint) {
      return this.connection.resumeQuery(checkpoint);
   }

   private <T> Iterator<T> resultIterator(final IndependentObjectSet results) {
      return results.iterator();
   }

   @Override
   public SourceContentBatch getChanges(byte[] checkpoint) throws Exception {

      return null;
   }

   @Override
   public void shutDown(byte[] checkpoint) throws Exception {
      this.connection = null;
   }

   @SuppressWarnings("unchecked")
   private <T> T getValue(final String name) throws MissingPropertyException {
      return (T) MapUtils.getObject(this.properties, name);
   }

   @SuppressWarnings("unchecked")
   private <T> T getValue(final String name, T defaultValue) {
      return (T) MapUtils.getObject(this.properties, name, defaultValue);
   }

   private String getString(final String name) throws MissingPropertyException {
      final String value = getValue(name);
      if (StringUtils.isEmpty(value)) {
         throw new MissingPropertyException(name);
      }

      return value;
   }

   private String processVariables(final String value) throws MissingPropertyException {
      final StringSubstitutor subs = new StringSubstitutor(new PropertyNameLookup());
      return subs.replace(getString("sql"));
   }

   private class PropertyNameLookup implements StringLookup {
      @Override
      public String lookup(String key) {
         try {
            return (String) FieldUtils.readDeclaredStaticField(PropertyNames.class, key, true);
         } catch (Exception e) {
            log.warn("{} while looking up '{}' in PropertyNames.", e.getClass().getSimpleName(), key);
            return null;
         }
      }
   }
}

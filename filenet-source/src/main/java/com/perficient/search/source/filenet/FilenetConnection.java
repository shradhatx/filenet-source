package com.perficient.search.source.filenet;

import javax.security.auth.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.collection.PageIterator;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.UserContext;

public class FilenetConnection {
   private static final Logger log = LoggerFactory.getLogger(FilenetConnection.class);

   private Connection  connection;
   private Subject     subject;
   private ObjectStore objectStore;

   public FilenetConnection(final String ceurl, final String username, final String password, final String osname) {
      log.debug("Connecting to Filenet url='{}' as user='{}'", ceurl, username);
      this.connection = Factory.Connection.getConnection(ceurl);
      this.subject    = UserContext.createSubject(this.connection, username, password, "FileNetP8");

      try (FilenetContext context = getContext()) {
         final Domain domain = Factory.Domain.fetchInstance(this.connection, null, null);
         this.objectStore = Factory.ObjectStore.fetchInstance(domain, osname, null);

         log.debug("Filenet domain='{}' and objectstore='{}'", domain.get_Name(), osname);
      }
   }

   public FilenetContext getContext() {
      return new FilenetContext(this.subject);
   }

   public IndependentObjectSet query(final Integer batchSize, final String sql) {
      log.debug("Querying Filenet with batchSize={} and sql={}", batchSize, sql);

      try (FilenetContext context = getContext()) {
         final SearchScope scope = new SearchScope(this.objectStore);
         return scope.fetchObjects(new SearchSQL(sql), batchSize, null, true);
      }
   }

   public PageIterator resumeQuery(byte[] checkpoint) {
      return Factory.PageIterator.resumeInstance(this.connection, checkpoint);
   }
}

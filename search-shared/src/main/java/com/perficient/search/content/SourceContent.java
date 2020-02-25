package com.perficient.search.content;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SourceContent implements Serializable {
   private static final long   serialVersionUID = -2376462167245798432L;
   private String              id;
   private Map<String, Object> metadata         = new HashMap<>();
   private ContentSecurity     securityObject;
   private ContentData         documentRawContent;

   public SourceContent() {}

   public SourceContent(final String id, final Map<String, Object> metadata, final ContentSecurity securityObject, final ContentData documentRawContent) {
      this.id = id;
      this.metadata.putAll(metadata);
      this.securityObject     = securityObject;
      this.documentRawContent = documentRawContent;
   }

   public String getId() {
      return this.id;
   }

   public void setId(final String id) {
      this.id = id;
   }

   public Map<String, Object> getMetadata() {
      return this.metadata;
   }

   public ContentSecurity getSecurityObject() {
      return this.securityObject;
   }

   public ContentData getDocumentRawContent() {
      return this.documentRawContent;
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", this.id).append("metadata", this.metadata).toString();
   }
}

package com.perficient.search.content;

import java.io.Serializable;

public class ContentData implements Serializable {

   private static final long serialVersionUID = -6836334260780748482L;

   public enum ContentType {
      Reference,
      Text,
      Binary,
      Not_Loaded_Yet,
      Empty,
      FilePath
   }

   private final String      mimeType;
   private final ContentType contentType;
   private String            stringContent;
   private byte[]            binaryContent;
   private String            filePath;

   public ContentData(final ContentType type, final String mimeType) {
      this.contentType = type;
      this.mimeType    = mimeType;
   }

   public void setStringContent(final String content) {
      this.stringContent = content;
   }

   public void setBinaryContent(final byte[] content) {
      this.binaryContent = content;
   }

   public String getMimeType() {
      return this.mimeType;
   }

   public ContentType getContentType() {
      return this.contentType;
   }

   public byte[] getBinaryContent() {
      return this.binaryContent;
   }

   public String getStringContent() {
      return this.stringContent;
   }

   public String getFilePath() {
      return this.filePath;
   }

   public void setFilePath(final String filePath) {
      this.filePath = filePath;
   }

}

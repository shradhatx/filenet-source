package com.perficient.search.content;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.text.TextStringBuilder;

public class SourceContentBatch implements Iterable<SourceContent>, Serializable {
   private static final long serialVersionUID = 3468008827842983062L;

   private final List<SourceContent> contents   = new LinkedList<>();
   private final SourceCheckpoint    checkpoint = new SourceCheckpoint();
   private Boolean                   hasMore    = false;

   public SourceContentBatch() {}

   public void addContent(final SourceContent content) {
      this.contents.add(content);
   }

   public int getBatchSize() {
      return this.contents.size();
   }

   public List<SourceContent> getContents() {
      return this.contents;
   }

   public Boolean getHasMore() {
      return this.hasMore;
   }

   public void setHasMore(final Boolean hasMore) {
      this.hasMore = hasMore;
   }

   public byte[] getCheckpoint() {
      return this.checkpoint.get();
   }

   public void setCheckpoint(final byte[] checkpoint) {
      this.checkpoint.set(checkpoint);
   }

   @Override
   public Iterator<SourceContent> iterator() {
      return this.contents.iterator();
   }

   @Override
   public String toString() {
      final TextStringBuilder str = new TextStringBuilder()
            .append("[\n    ")
            .appendWithSeparators(this.contents, ",\n    ")
            .append("]");

      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("\n  contents", str.toString())
            .append("hasMore", this.hasMore)
            .append("\n  checkpoint", this.checkpoint).toString();
   }
}

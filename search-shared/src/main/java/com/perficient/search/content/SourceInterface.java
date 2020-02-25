package com.perficient.search.content;

import java.util.Map;

public interface SourceInterface {
   void init(Map<String, Object> properties) throws Exception;

   SourceContentBatch getContent(byte[] checkpoint) throws Exception;

   SourceContentBatch getChanges(byte[] checkpoint) throws Exception;

   void shutDown(byte[] checkpoint) throws Exception;
}

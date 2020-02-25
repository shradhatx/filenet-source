package com.perficient.search.content;

public interface TargetInterface {
   void init() throws Exception;
   // public abstract FrameworkJavaObjectBatch getContent(byte[] checkpoint) throws Exception;
   // public abstract FrameworkJavaObjectBatch getChanges(byte[] checkpoint) throws Exception;

   void transmitBatch(SourceContentBatch batch) throws Exception;

   void trasmitSingle(SourceContent item) throws Exception;

   void shutDown() throws Exception;
}

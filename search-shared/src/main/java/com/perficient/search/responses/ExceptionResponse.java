package com.perficient.search.responses;

import java.util.ArrayList;

public class ExceptionResponse extends AbstractResponse {
   private ArrayList<String> stackTrace = new ArrayList<>();

   public ExceptionResponse() {
      this.setResponse(Response.FAILED);
   }

   public ArrayList<String> getStackTrace() {
      return this.stackTrace;
   }

   public void setStackTrace(final ArrayList<String> stackTrace) {
      this.stackTrace = stackTrace;
   }
}

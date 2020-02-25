package com.perficient.search.responses;

public enum Response {
   OK("OK"),
   FAILED("FAILED"),
   WARNING("WARNING"),
   EMPTY("");

   private final String value;

   private Response(final String value) {
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }

   @Override
   public String toString() {
      return this.getValue();
   }
}

package com.perficient.search.source.filenet;

public class MissingPropertyException extends Exception {
   private static final long serialVersionUID = 1L;

   public MissingPropertyException(final String name) {
      super(String.format("The required property '%s' is missing.", name));
   }
}

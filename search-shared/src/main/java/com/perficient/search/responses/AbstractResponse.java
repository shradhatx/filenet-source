package com.perficient.search.responses;

public abstract class AbstractResponse {
   private Response response;

   public Response getResponse() {
      return this.response;
   }

   public void setResponse(final Response response) {
      this.response = response;
   }
}

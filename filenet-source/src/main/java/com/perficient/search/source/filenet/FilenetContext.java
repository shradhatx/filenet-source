package com.perficient.search.source.filenet;

import javax.security.auth.Subject;

import com.filenet.api.util.UserContext;

public class FilenetContext implements AutoCloseable {
   public FilenetContext(final Subject subject) {
      UserContext.get().pushSubject(subject);
   }
   
   @Override
   public void close() {
      UserContext.get().popSubject();
   }
}

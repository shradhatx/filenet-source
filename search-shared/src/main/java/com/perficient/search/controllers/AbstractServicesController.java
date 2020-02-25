package com.perficient.search.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.search.responses.ExceptionResponse;

@Component
public abstract class AbstractServicesController {
   private void constructTraceMessage(final Throwable e, final List<String> stackTrace, final Map<String, String> messagesMap,
         final boolean isCause) {
      if (e == null) {
         // base case, return
         return;
      }

      // construct message for this exception and its stack trace
      final String prefix = isCause ? "Caused by: " : "";
      stackTrace.add(prefix + e.toString());
      final StackTraceElement elements[] = e.getStackTrace();
      for (int i = 0; i < elements.length; i++) {
         final StackTraceElement element  = elements[i];
         final String            stackMsg = "at " + element.getClassName() + "." + element.getMethodName() + "(" + element.getFileName() + ":"
               + element.getLineNumber() + ")";
         if (messagesMap.get(stackMsg) != null) {
            // check to see if this message already exists in the map, if so, we can stop, since it's been logged by a previous parent
            stackTrace.add("... " + (elements.length - i) + " more");
            break;
         }

         // add this message to list and to the map for the next child cause to check
         stackTrace.add(stackMsg);
         messagesMap.put(stackMsg, stackMsg);

      }

      // recursively call this function for the next cause
      constructTraceMessage(e.getCause(), stackTrace, messagesMap, true);
   }

   /**
    * Default exception handler for Spring controller classes. Sub-classes can override as needed.
    */
   @ExceptionHandler(Exception.class)
   @ResponseBody()
   public ResponseEntity<ExceptionResponse> myExceptionHandler(final Exception e) {
      final ExceptionResponse response   = new ExceptionResponse();
      final List<String>      stackTrace = response.getStackTrace();

      // call recursive function to construct entire stack trace
      final Map<String, String> messagesMap = new HashMap<>();
      constructTraceMessage(e, stackTrace, messagesMap, false);

      final ResponseEntity<ExceptionResponse> entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      return entity;
   }
}

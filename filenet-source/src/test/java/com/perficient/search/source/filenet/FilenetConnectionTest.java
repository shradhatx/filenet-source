package com.perficient.search.source.filenet;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.perficient.search.source.filenet.FilenetConnection;
import com.perficient.search.source.filenet.MissingPropertyException;

class FilenetConnectionTest {
   @Test
   @DisplayName("Using valid properties")
   void testPropertiesProvided() {
      assertDoesNotThrow(() -> new FilenetConnection("http://chicago.baseline.dev.tritek.com:9081/wsi/FNCEWS40MTOM", "ceadmin", "Tritek1", "OSCSS"));
   }

   @Test
   @DisplayName("With required properties missing")
   void testMissingProperties() {
      assertThrows(MissingPropertyException.class, () -> new FilenetConnection("", "", "", ""));
   }
}

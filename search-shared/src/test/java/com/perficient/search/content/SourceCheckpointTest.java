package com.perficient.search.content;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("SourceCheckpoint Testing")
public class SourceCheckpointTest {
   private static final Logger log = LoggerFactory.getLogger(SourceCheckpointTest.class);

   private static final int TEST_REPETITIONS = 10;
   private static final int START_LEN        = 200;
   private static final int END_LEN          = 1000;

   @ParameterizedTest(name = "Checkpoint with {0} bytes")
   @MethodSource("randomLen")
   public void testSetGet(int len) {
      final byte[] test = RandomUtils.nextBytes(len);

      final SourceCheckpoint checkpoint = new SourceCheckpoint();

      // Set random bytes
      checkpoint.set(test);
      log.info("Checkpoint {}", checkpoint);

      // Make sure we can get back the same random bytes
      assertArrayEquals(test, checkpoint.get(), "Checkpoint bytes aren't equal.");
   }

   @ParameterizedTest(name = "Checkpoint with {0} bytes")
   @MethodSource("randomLen")
   public void testEncodeDecode(int len) throws IOException {
      // Encode the random bytes
      final SourceCheckpoint checkpoint = new SourceCheckpoint(RandomUtils.nextBytes(len));
      final String           encoded    = checkpoint.encode();

      // Decode should get back the original random bytes
      final SourceCheckpoint checkpoint2 = SourceCheckpoint.decode(encoded);
      assertArrayEquals(checkpoint.get(), checkpoint2.get(), "Checkpoint bytes aren't equal.");
   }

   static IntStream randomLen() {
      return IntStream.generate(() -> RandomUtils.nextInt(START_LEN, END_LEN)).limit(TEST_REPETITIONS);
   }
}

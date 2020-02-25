package com.perficient.search.content;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

public class SourceCheckpoint implements Serializable {
   private static final long serialVersionUID = -1243657826317134900L;
   private static final int  TOSTRING_LEN     = 80;

   private byte[] checkpoint;

   public SourceCheckpoint() {}

   public SourceCheckpoint(final byte[] checkpoint) {
      this.checkpoint = checkpoint;
   }

   public void set(byte[] checkpoint) {
      this.checkpoint = checkpoint;
   }

   public byte[] get() {
      return this.checkpoint;
   }

   @Override
   public String toString() {
      return StringUtils.abbreviate(Hex.encodeHexString(this.checkpoint), TOSTRING_LEN);
   }

   public String encode() throws IOException {
      // A byte array stream to hold the compressed output
      final ByteArrayOutputStream bos = new ByteArrayOutputStream(checkpoint.length / 2);

      // Compress the input bytes
      try (final DeflaterOutputStream dos = new DeflaterOutputStream(bos, new Deflater(Deflater.BEST_COMPRESSION))) {
         dos.write(checkpoint);
      }

      // Get the compressed bytes
      final byte[] output = bos.toByteArray();

      // Convert the bytes to a string using Base64 encoding
      return Base64.getEncoder().encodeToString(output);

   }

   public static SourceCheckpoint decode(final String encoded) throws IOException {
      // Convert the Base64 encoded string back into bytes
      final byte[] input = Base64.getDecoder().decode(encoded);

      // A byte array stream to hold the uncompressed output
      final ByteArrayOutputStream bos = new ByteArrayOutputStream();

      // Uncompress the input bytes
      try (final InflaterOutputStream ios = new InflaterOutputStream(bos)) {
         ios.write(input);
      }

      // Return the bytes as a checkpoint
      return new SourceCheckpoint(bos.toByteArray());
   }
}

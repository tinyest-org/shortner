package org.tyniest;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.UUID;

/**
 * This call helps build shorter string representations of uuid4
 * by encoding it with b64 instead of b16
 */
public class UuidHelper {
    private static final Encoder BASE64_URL_ENCODER = Base64.getUrlEncoder().withoutPadding();

  public static String uuidToB64(UUID uuid) {
    final var bytes = uuidToBytes(uuid);
    return BASE64_URL_ENCODER.encodeToString(bytes);
  }

  private static byte[] uuidToBytes(UUID uuid) {
    final var bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }

  public static String getCompactUUID4() {
    return UuidHelper.uuidToB64(UUID.randomUUID());
  }
}

package utils.encryptionMethods.core;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicConversions {

    public static byte[] encodeBytes(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }

    public static byte[] decodeBytes(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }

    public static byte[] stringToBytes(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }

    public static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] stringToBase64Bytes(String string) {
        return encodeBytes(stringToBytes(string));
    }

    public static String base64BytesToString(byte[] bytes) {
        return bytesToString(decodeBytes(bytes));
    }
}

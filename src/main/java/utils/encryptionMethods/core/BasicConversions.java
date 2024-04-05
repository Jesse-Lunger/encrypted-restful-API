package utils.encryptionMethods.core;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicConversions {

    public static byte[] stringToBytes(String string){
        return Base64.getEncoder().encode(string.getBytes(StandardCharsets.UTF_8));
    }

    public static String bytesToString(byte[] base64Bytes){
        byte[] decodedBytes = Base64.getDecoder().decode(base64Bytes);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}

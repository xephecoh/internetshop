package Utills;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {
    public static String encrypt(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] resultByteArray = messageDigest.digest();
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : resultByteArray) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

}

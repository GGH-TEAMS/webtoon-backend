package com.ggh.core.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Objects;

public class AES256CryptoUtil {

    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";

    public static String encryptText(String plainText, byte[] key, byte[] iv) throws Exception {
        validateInput(plainText, key, iv);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv, 0, cipher.getBlockSize());

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptText(String encryptedText, byte[] key, byte[] iv) throws Exception {
        validateInput(encryptedText, key, iv);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv, 0, cipher.getBlockSize());

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    private static void validateInput(String text, byte[] key, byte[] iv) {
        Objects.requireNonNull(text, "Text must not be null");
        Objects.requireNonNull(key, "Secret key must not be null");
        Objects.requireNonNull(iv, "Initialization vector must not be null");
    }
}

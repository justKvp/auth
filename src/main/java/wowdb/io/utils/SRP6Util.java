package wowdb.io.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SRP6Util {
    private static final BigInteger TC_g = BigInteger.valueOf(7);
    private static final BigInteger TC_N = new BigInteger("894B645E89E1535BBDAD5B8B290650530801B18EBFBF5E8FAB3C82872A3E9BB7", 16);

    public static byte[] makeSHA1fromArgs(byte[]... args) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        for (byte[] arg : args) {
            md.update(arg);
        }
        return md.digest();
    }

    private static BigInteger getX(byte[] salt, String username, String password) throws NoSuchAlgorithmException {
        String prepared = String.format("%s:%s", username, password).toUpperCase();
        byte[] h1 = makeSHA1fromArgs(prepared.getBytes(StandardCharsets.UTF_8));
        byte[] hashX = makeSHA1fromArgs(salt, h1);

        AUtil.reverse(hashX);

        return new BigInteger(1, hashX);  // '1' указывает, что BigInteger всегда положительный
    }

    private static byte[] finishVerifier(BigInteger source) {
        // Преобразуем BigInteger в байты и инвертируем их
        byte[] verifier = source.toByteArray();
        if (verifier.length > 32) {
            byte[] fixedVerifier = new byte[32];
            for (int i = 0; i < 32; i++) {
                fixedVerifier[i] = verifier[i];
            }
            verifier = fixedVerifier;
        }
        AUtil.reverse(verifier);
        return verifier;
    }

    public static byte[] calculateSRP6TCVerifier(String username, String password, byte[] salt) throws NoSuchAlgorithmException {
        BigInteger x = getX(salt, username, password);
        BigInteger ver = TC_g.modPow(x, TC_N);
        return finishVerifier(ver);
    }

    public static Boolean verifySRP6(String username, String password, byte[] salt, byte[] verifier) throws NoSuchAlgorithmException {
        byte[] generated = calculateSRP6TCVerifier(username, password, salt);
        return MessageDigest.isEqual(generated, verifier);
    }

    public static byte[] generateRandomSalt(int numBytes) {
        byte[] salt = new byte[numBytes];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }
}

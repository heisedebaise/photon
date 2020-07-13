package org.lpw.photon.crypto;

import org.lpw.photon.util.Codec;
import org.lpw.photon.util.Logger;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author lpw
 */
@Component("photon.crypto.digest")
public class DigestImpl implements Digest {
    private static final String MD5 = "MD5";
    private static final String SHA1 = "SHA1";
    private static final String SHA_256 = "SHA-256";
    private static final String SHA_512 = "SHA-512";

    @Inject
    private Codec codec;
    @Inject
    private Logger logger;

    @Override
    public String md5(String text) {
        return text == null ? null : digestHex(MD5, text.getBytes());
    }

    @Override
    public String md5(byte[] bytes) {
        return digestHex(MD5, bytes);
    }

    @Override
    public String sha1(String text) {
        return text == null ? null : digestHex(SHA1, text.getBytes());
    }

    @Override
    public String sha1(byte[] bytes) {
        return digestHex(SHA1, bytes);
    }

    @Override
    public String sha256(String text) {
        return text == null ? null : digestHex(SHA_256, text.getBytes());
    }

    @Override
    public String sha256(byte[] bytes) {
        return digestHex(SHA_256, bytes);
    }

    @Override
    public String sha512(String text) {
        return text == null ? null : digestHex(SHA_512, text.getBytes());
    }

    @Override
    public String sha512(byte[] bytes) {
        return digestHex(SHA_512, bytes);
    }

    private String digestHex(String algorithm, byte[] bytes) {
        return bytes == null ? null : codec.hex(digest(algorithm, bytes));
    }

    @Override
    public byte[] digest(String algorithm, byte[] bytes) {
        if (bytes == null)
            return null;

        try {
            return MessageDigest.getInstance(algorithm).digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            logger.warn(e, "取消息摘要[{}]时发生异常！", algorithm);

            return null;
        }
    }

    @Override
    public byte[] hmacSHA1(byte[] key, byte[] bytes) {
        if (key == null || bytes == null)
            return null;

        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
            Mac mac = Mac.getInstance(signingKey.getAlgorithm());
            mac.init(signingKey);
            return mac.doFinal(bytes);
        } catch (Throwable throwable) {
            logger.warn(throwable, "取消息摘要[{}]时发生异常！", "HmacSHA1");

            return null;
        }
    }
}

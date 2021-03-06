package org.lpw.photon.crypto;

/**
 * 消息摘要。
 */
public interface Digest {
    /**
     * 提取MD5消息摘要。
     *
     * @param text 源字符串。
     * @return MD5消息摘要；如果提取失败将返回null。
     */
    String md5(String text);

    /**
     * 提取MD5消息摘要。
     *
     * @param bytes 源数据。
     * @return MD5消息摘要；如果提取失败将返回null。
     */
    String md5(byte[] bytes);

    /**
     * 提取SHA1消息摘要。
     *
     * @param text 源字符串。
     * @return SHA1消息摘要；如果提取失败将返回null。
     */
    String sha1(String text);

    /**
     * 提取SHA1消息摘要。
     *
     * @param bytes 源数据。
     * @return SHA1消息摘要；如果提取失败将返回null。
     */
    String sha1(byte[] bytes);

    /**
     * 提取SHA256消息摘要。
     *
     * @param text 源字符串。
     * @return SHA256消息摘要；如果提取失败将返回null。
     */
    String sha256(String text);

    /**
     * 提取SHA256消息摘要。
     *
     * @param bytes 源数据。
     * @return SHA256消息摘要；如果提取失败将返回null。
     */
    String sha256(byte[] bytes);

    /**
     * 提取SHA512消息摘要。
     *
     * @param text 源字符串。
     * @return SHA512消息摘要；如果提取失败将返回null。
     */
    String sha512(String text);

    /**
     * 提取SHA512消息摘要。
     *
     * @param bytes 源数据。
     * @return SHA512消息摘要；如果提取失败将返回null。
     */
    String sha512(byte[] bytes);

    /**
     * 提取消息摘要。
     *
     * @param algorithm 算法。
     * @param bytes     源数据。
     * @return 消息摘要；如果提取失败则返回null。
     */
    byte[] digest(String algorithm, byte[] bytes);

    /**
     * HmacSHA1消息摘要。
     *
     * @param key   密钥。
     * @param bytes 源数据。
     * @return HmacSHA1消息摘要；如果提取失败则返回null。
     */
    byte[] hmacSHA1(byte[] key, byte[] bytes);

    /**
     * SHA256withRSA消息摘要。
     *
     * @param key   私钥。
     * @param bytes 源数据。
     * @return SHA256withRSA消息摘要；如果提取失败则返回null。
     */
    byte[] sha256Rsa(byte[] key, byte[] bytes);
}

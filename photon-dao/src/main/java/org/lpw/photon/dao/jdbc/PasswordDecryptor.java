package org.lpw.photon.dao.jdbc;

/**
 * @author lpw
 */
public interface PasswordDecryptor {
    /**
     * 解密密码。
     *
     * @param password 加密密码。
     * @return 密码。
     */
    String decrypt(String password);
}

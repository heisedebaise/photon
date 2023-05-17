package org.lpw.photon.util;

public interface Mask {
    /**
     * 隐藏手机号中间4个数字，以星号代替。
     *
     * @param mobile 手机号。
     * @return 处理后的手机号。
     */
    String mobile(String mobile);
}

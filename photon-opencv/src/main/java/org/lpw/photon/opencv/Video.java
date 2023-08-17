package org.lpw.photon.opencv;

import java.awt.image.BufferedImage;

public interface Video {
    /**
     * 截取首帧。
     *
     * @param path 视频文件路径。
     * @return 首帧图片，失败则返回null。
     */
    BufferedImage firstFrame(String path);
}

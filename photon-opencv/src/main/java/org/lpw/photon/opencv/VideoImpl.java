package org.lpw.photon.opencv;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.lpw.photon.util.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.awt.image.BufferedImage;

@Component("photon.opencv.video")
public class VideoImpl implements Video {
    @Inject
    private Logger logger;

    @Override
    public BufferedImage firstFrame(String path) {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(path); Java2DFrameConverter converter = new Java2DFrameConverter()) {
            for (int i = 0, length = grabber.getLengthInFrames(); i < length; i++) {
                Frame frame = grabber.grabFrame();
                if (frame.image != null)
                    return converter.convert(frame);
            }
        } catch (Throwable throwable) {
            logger.warn(throwable, "截取视频[{}]首帧时发生异常！", path);
        }

        return null;
    }
}

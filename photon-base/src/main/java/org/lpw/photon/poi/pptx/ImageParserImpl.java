package org.lpw.photon.poi.pptx;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.*;
import org.lpw.photon.poi.StreamWriter;
import org.lpw.photon.util.Http;
import org.lpw.photon.util.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lpw
 */
@Component("photon.poi.pptx.image")
public class ImageParserImpl extends ImageParserSupport implements Parser {
    @Inject
    private Http http;
    @Inject
    private Logger logger;

    @Override
    public String getType() {
        return TYPE_IMAGE;
    }

    @Override
    public boolean parse(XMLSlideShow xmlSlideShow, XSLFSlide xslfSlide, JSONObject object) {
        String image = object.getString(getType());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Map<String, String> map = new HashMap<>();
        http.get(image, null, null, map, outputStream);
        if (map.isEmpty())
            return false;

        try {
            String contenType = map.get("Content-Type");
            XSLFPictureData xslfPictureData = xmlSlideShow.addPicture(parserHelper.getImage(object, contenType, outputStream),
                    getPictureType(image, contenType));
            parse(xslfSlide, xslfPictureData, object);

            return true;
        } catch (Exception e) {
            logger.warn(e, "解析图片[{}]时发生异常！", object.toJSONString());

            return false;
        }
    }

    private PictureData.PictureType getPictureType(String url, String contentType) {
        switch (contentType) {
            case "image/jpeg":
                return PictureData.PictureType.JPEG;
            case "image/gif":
                return PictureData.PictureType.GIF;
            default:
                if (!contentType.equals("image/png"))
                    logger.warn(null, "未处理图片类型[{}:{}]！", url, contentType);
                return PictureData.PictureType.PNG;
        }
    }

    @Override
    public boolean parse(JSONObject object, XSLFShape xslfShape, StreamWriter streamWriter) {
        XSLFPictureShape xslfPictureShape = (XSLFPictureShape) xslfShape;
        Insets insets = xslfPictureShape.getClipping();
        if (insets != null) {
            JSONObject clip = new JSONObject();
            clip.put("x", numeric.toInt(insets.left * 0.025D));
            clip.put("y", numeric.toInt(insets.top * 0.025D));
            clip.put("width", numeric.toInt((insets.right - insets.left) * 0.025D));
            clip.put("height", numeric.toInt((insets.bottom - insets.top) * 0.025D));
            if (clip.getIntValue("x") > 0 || clip.getIntValue("y") > 0
                    || clip.getIntValue("width") > 0 || clip.getIntValue("height") > 0)
                object.put("clip", clip);
        }

        XSLFPictureData xslfPictureData = xslfPictureShape.getPictureData();
        try {
            InputStream inputStream = xslfPictureData.getInputStream();
            object.put(getType(), streamWriter.write(xslfPictureData.getContentType(), xslfPictureData.getFileName(), inputStream));
            inputStream.close();
        } catch (IOException e) {
            logger.warn(e, "保存图片[{}:{}]流数据时发生异常！", xslfPictureData.getContentType(), xslfPictureData.getFileName());
        }

        return true;
    }
}

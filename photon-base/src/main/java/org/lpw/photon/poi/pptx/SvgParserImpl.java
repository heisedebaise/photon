package org.lpw.photon.poi.pptx;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.lpw.photon.poi.StreamWriter;
import org.lpw.photon.util.Image;
import org.lpw.photon.util.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;

/**
 * @author lpw
 */
@Component("photon.poi.pptx.svg")
public class SvgParserImpl extends ImageParserSupport implements Parser {
    @Inject
    private Image image;
    @Inject
    private Logger logger;

    @Override
    public String getType() {
        return TYPE_SVG;
    }

    @Override
    public boolean parse(XMLSlideShow xmlSlideShow, XSLFSlide xslfSlide, JSONObject object) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            if (!image.svg2png(object.getString("svg"), object.getIntValue("width"), object.getIntValue("height"), outputStream))
                return false;

            XSLFPictureData xslfPictureData = xmlSlideShow.addPicture(
                    parserHelper.getImage(object, "image/png", outputStream), PictureData.PictureType.PNG);
            parse(xslfSlide, xslfPictureData, object);

            return true;
        } catch (Throwable e) {
            logger.warn(e, "解析SVG图片[{}]时发生异常！", object.toJSONString());

            return false;
        }
    }

    @Override
    public boolean parse(JSONObject object, XSLFShape xslfShape, StreamWriter writer) {
        return false;
    }
}

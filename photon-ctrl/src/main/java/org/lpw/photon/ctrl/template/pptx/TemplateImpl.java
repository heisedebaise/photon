package org.lpw.photon.ctrl.template.pptx;

import com.alibaba.fastjson.JSONObject;
import org.lpw.photon.ctrl.context.Response;
import org.lpw.photon.ctrl.template.TemplateSupport;
import org.lpw.photon.ctrl.template.Templates;
import org.lpw.photon.poi.Pptx;
import org.lpw.photon.util.Coder;
import org.lpw.photon.util.Context;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author lpw
 */
@Controller("photon.ctrl.template.pptx")
public class TemplateImpl extends TemplateSupport {
    @Inject
    private Context context;
    @Inject
    private Coder coder;
    @Inject
    private Pptx pptx;
    @Inject
    private Response response;

    @Override
    public String getType() {
        return Templates.PPTX;
    }

    @Override
    public String getContentType() {
        return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    }

    @Override
    public void process(String name, Object data, OutputStream outputStream) throws IOException {
        if (!(data instanceof JSONObject))
            return;

        JSONObject object = (JSONObject) data;
        if (object.containsKey("filename"))
            response.setHeader("Content-Disposition", "attachment; filename*=" + context.getCharset(null)
                    + "''" + coder.encodeUrl(object.getString("filename"), null) + ".pptx");
        pptx.write(object, outputStream);
    }
}

package org.lpw.photon.ctrl.template.excel;

import org.lpw.photon.ctrl.template.TemplateSupport;
import org.lpw.photon.ctrl.template.Templates;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author lpw
 */
@Controller("photon.ctrl.template.excel")
public class TemplateImpl extends TemplateSupport {
    @Override
    public String getType() {
        return Templates.EXCEL;
    }

    @Override
    public String getContentType() {
        return "application/vnd.ms-excel";
    }

    @Override
    public void process(String name, Object data, OutputStream outputStream) throws IOException {
        if (!(data instanceof ExcelBuilder))
            throw new NullPointerException("data参数必须为ExcelBuilder实例！");

        ((ExcelBuilder) data).write(outputStream);
    }
}

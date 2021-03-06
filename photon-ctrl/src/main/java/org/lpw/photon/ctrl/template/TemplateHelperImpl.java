package org.lpw.photon.ctrl.template;

import org.springframework.stereotype.Controller;

@Controller("photon.ctrl.template.helper")
public class TemplateHelperImpl implements TemplateHelper {
    protected ThreadLocal<String> template = new ThreadLocal<>();

    @Override
    public String getTemplate() {
        return template.get();
    }

    @Override
    public void setTemplate(String template) {
        this.template.set(template);
    }
}

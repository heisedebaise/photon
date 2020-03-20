package org.lpw.photon.ctrl.template;

import org.lpw.photon.bean.BeanFactory;
import org.lpw.photon.bean.ContextRefreshedListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lpw
 */
@Controller("photon.ctrl.templates")
public class TemplatesImpl implements Templates, ContextRefreshedListener {
    @Value("${photon.ctrl.template.type:json}")
    protected String type;
    protected Map<String, Template> map;

    @Override
    public Template get() {
        return get(type);
    }

    @Override
    public Template get(String type) {
        return map.get(type);
    }

    @Override
    public int getContextRefreshedSort() {
        return 6;
    }

    @Override
    public void onContextRefreshed() {
        if (map != null)
            return;

        map = new HashMap<>();
        BeanFactory.getBeans(Template.class).forEach(template -> map.put(template.getType(), template));
        map.put("", map.get(type));
    }
}

package org.lpw.photon.ctrl;

import org.lpw.photon.util.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("photon.ctrl.helper")
public class CtrlHelperImpl implements CtrlHelper {
    @Inject
    private Validator validator;
    @Value("${photon.ctrl.root:}")
    private String root;
    private final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Override
    public void setRoot(String root) {
        threadLocal.set(root);
    }

    @Override
    public String url(String uri) {
        if (threadLocal.get() != null)
            return threadLocal.get() + uri;

        return validator.isEmpty(root) ? null : (root + uri);
    }
}

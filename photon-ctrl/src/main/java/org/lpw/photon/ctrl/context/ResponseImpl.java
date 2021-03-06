package org.lpw.photon.ctrl.context;

import org.lpw.photon.ctrl.Codec;
import org.lpw.photon.ctrl.execute.Executor;
import org.lpw.photon.ctrl.execute.ExecutorHelper;
import org.lpw.photon.ctrl.template.Template;
import org.lpw.photon.ctrl.template.TemplateHelper;
import org.lpw.photon.ctrl.template.Templates;
import org.lpw.photon.util.Logger;
import org.lpw.photon.util.Validator;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Optional;

@Controller("photon.ctrl.context.response")
public class ResponseImpl implements Response, ResponseAware {
    @Inject
    private Validator validator;
    @Inject
    private Logger logger;
    @Inject
    private ExecutorHelper executorHelper;
    @Inject
    private Templates templates;
    @Inject
    private TemplateHelper templateHelper;
    @Inject
    private Optional<Codec> codec;
    private ThreadLocal<ResponseAdapter> adapter = new ThreadLocal<>();
    private ThreadLocal<String> contentType = new ThreadLocal<>();

    @Override
    public void setContentType(String contentType) {
        if (logger.isDebugEnable())
            logger.debug("设置Content-Type[{}]", contentType);

        this.contentType.set(contentType);
        adapter.get().setContentType(contentType);
    }

    @Override
    public void setHeader(String name, String value) {
        adapter.get().setHeader(name, value);
    }

    @Override
    public OutputStream getOutputStream() {
        return adapter.get().getOutputStream();
    }

    @Override
    public void write(Object object) {
        if (object == null)
            return;

        try {
            Executor executor = executorHelper.get();
            Template template = executor == null ? templates.get() : executor.getTemplate();
            String view = executor == null ? null : executor.getView();
            if (!validator.isEmpty(templateHelper.getTemplate())) {
                view = templateHelper.getTemplate();
                templateHelper.setTemplate(null);
            }
            setContentType(template);
            if (!codec.isPresent()) {
                template.process(view, object, getOutputStream());
                adapter.get().send();

                return;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            template.process(view, object, baos);
            baos.close();
            getOutputStream().write(codec.get().encode(baos.toByteArray()));
            adapter.get().send();
        } catch (Exception e) {
            logger.warn(e, "返回输出结果时发生异常！");
        }
    }

    private void setContentType(Template template) {
        if (!validator.isEmpty(contentType.get()))
            return;

        if (logger.isDebugEnable())
            logger.debug("使用Content-Type[{}]", template.getContentType());
        adapter.get().setContentType(template.getContentType());
    }

    @Override
    public void redirectTo(String url) {
        if (logger.isDebugEnable())
            logger.debug("跳转到：{}。", url);

        adapter.get().redirectTo(url);
    }

    @Override
    public void sendError(int code) {
        adapter.get().sendError(code);
    }

    @Override
    public void set(ResponseAdapter adapter) {
        this.adapter.set(adapter);
        contentType.remove();
    }
}

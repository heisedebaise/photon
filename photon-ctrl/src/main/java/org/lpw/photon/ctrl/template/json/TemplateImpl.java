package org.lpw.photon.ctrl.template.json;

import com.alibaba.fastjson.JSONObject;
import org.lpw.photon.ctrl.Dispatcher;
import org.lpw.photon.ctrl.Failure;
import org.lpw.photon.ctrl.context.Request;
import org.lpw.photon.ctrl.template.TemplateSupport;
import org.lpw.photon.ctrl.template.Templates;
import org.lpw.photon.dao.model.Model;
import org.lpw.photon.dao.model.ModelHelper;
import org.lpw.photon.dao.orm.PageList;
import org.lpw.photon.util.Json;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;

@Controller("photon.ctrl.template.json")
public class TemplateImpl extends TemplateSupport {
    @Inject
    private Json json;
    @Inject
    private ModelHelper modelHelper;
    @Inject
    private Request request;
    @Inject
    private Dispatcher dispatcher;

    @Override
    public String getType() {
        return Templates.JSON;
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    @SuppressWarnings("unchecked")
    public void process(String name, Object data, OutputStream outputStream) throws IOException {
        if (data instanceof Failure) {
            write(getFailure((Failure) data), outputStream);

            return;
        }

        if (data instanceof Model)
            data = modelHelper.toJson((Model) data);
        else if (data instanceof PageList)
            data = ((PageList<? extends Model>) data).toJson();
        else if (data instanceof String)
            data = json((String) data);

        write(pack(data), outputStream);
    }

    private Object json(String string) {
        if (string.length() == 0)
            return string;

        char ch = string.charAt(0);
        if (ch == '{')
            return json.toObject(string);

        if (ch == '[')
            return json.toArray(string);

        return string;
    }

    private Object pack(Object object) {
        if (object instanceof JSONObject) {
            JSONObject json = (JSONObject) object;
            if (json.containsKey("code") && (json.containsKey("data") || json.containsKey("message"))) {
                putIdTime(json);

                return object;
            }
        }

        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("data", object);
        putIdTime(json);

        return json;
    }

    private void putIdTime(JSONObject object) {
        if (request.getId() != null)
            object.put("id", request.getId());
        object.put("time", dispatcher.getTime());
        object.put("timestamp", System.currentTimeMillis());
    }

    private void write(Object data, OutputStream outputStream) throws IOException {
        outputStream.write(json.toBytes(data));
    }
}

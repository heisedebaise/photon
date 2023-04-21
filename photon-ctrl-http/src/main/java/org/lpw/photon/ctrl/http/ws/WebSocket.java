package org.lpw.photon.ctrl.http.ws;

import org.lpw.photon.bean.BeanFactory;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(WsHelper.URI)
public class WebSocket {
    private WsHelper wsHelper;

    public WebSocket() {
        wsHelper = BeanFactory.getBean(WsHelper.class);
    }

    @OnOpen
    public void open(Session session) {
        wsHelper.open(session);
    }

    @OnMessage
    public void message(Session session, String message) {
        wsHelper.message(session, message);
    }

    @OnError
    public void error(Session session, Throwable throwable) {
        wsHelper.error(session, throwable);
    }

    @OnClose
    public void close(Session session) {
        wsHelper.close(session);
    }
}

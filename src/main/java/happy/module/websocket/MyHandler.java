package happy.module.websocket;

import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        System.out.println("connection established");
        WebSocketSessionUtil.add(getUserNameFromSession(session), session);
        WebSocketSessionUtil.test(getUserNameFromSession(session), "连接成功");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        System.out.println("receive a message." + message);

        WebSocketSessionUtil.broadcast(getUserNameFromSession(session), message);
    }

    @Override
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) throws Exception {
        WebSocketSessionUtil.remove(getUserNameFromSession(session));
        WebSocketSessionUtil.test(getUserNameFromSession(session), "连接失败");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus closeStatus) throws Exception {
        System.out.println("conection closed." + closeStatus);

        WebSocketSessionUtil.remove(getUserNameFromSession(session));
        WebSocketSessionUtil.test(getUserNameFromSession(session), "退出连接");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private String getUserNameFromSession(WebSocketSession session) {
        Map<String, Object> params = session.getAttributes();

        return params.get("userName").toString();
    }

}

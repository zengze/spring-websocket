package happy.module.websocket;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketSessionUtil {

    private static Map<String, WebSocketSession> clients = new ConcurrentHashMap<String, WebSocketSession>();

    public static void add(String userName, WebSocketSession session) {
        clients.put(userName, session);
    }

    public static WebSocketSession get(String userName) {
        return clients.get(userName);
    }

    public static void remove(String userName) {
        clients.remove(userName);
    }

    public static void broadcast(String userName, TextMessage message)
            throws Exception {

        TextMessage msg = new TextMessage(userName + ":" + message.getPayload());

        Set<String> allUsers = clients.keySet();

        for (String name : allUsers) {
            WebSocketSession session = clients.get(name);
            session.sendMessage(msg);
        }
    }

    public static void test(String userName, String info) throws Exception {

        TextMessage message = new TextMessage(userName + info);

        Set<String> allUsers = clients.keySet();

        for (String name : allUsers) {
            WebSocketSession session = clients.get(name);
            session.sendMessage(message);
        }
    }
}

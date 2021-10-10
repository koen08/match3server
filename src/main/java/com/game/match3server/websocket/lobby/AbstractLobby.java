package com.game.match3server.websocket.lobby;

import com.game.match3server.websocket.CMD;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractLobby {
    private static final Map<CMD, Object> beans = new HashMap<>();
    private static final Map<String, WebSocketSession> webSocketSessionMap = new HashMap<>();


    public Object getObject(CMD cmd) {
        return beans.get(cmd);
    }

    public void register(CMD cmd, Object obj) {
        beans.put(cmd, obj);
    }

    public void put(String login, WebSocketSession webSocketSession) {
        webSocketSessionMap.put(login, webSocketSession);
    }

    public void remove(String login) {
        webSocketSessionMap.remove(login);
    }

    public WebSocketSession get(String login) {
        return webSocketSessionMap.get(login);
    }

    public void sendMsg(String login, Object obj) throws IOException {
        if (webSocketSessionMap.containsKey(login)){
            get(login).sendMessage(new TextMessage(GsonHelper.toJson(obj)));
        }
    }
}

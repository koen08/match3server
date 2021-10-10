package com.game.match3server.websocket.lobby;

import com.game.match3server.web.GenericResponse;
import com.game.match3server.web.RequestParam;
import com.game.match3server.websocket.CMD;

import java.io.IOException;
import java.security.Principal;

public class PingPongLobby implements Lobby {
    @Override
    public GenericResponse<?> process(Object obj, Principal principal) throws IOException {
        RequestParam webSocketParam = (RequestParam) GsonHelper.fromJson(obj, RequestParam.class);
        webSocketParam.setParam("pong");
        return new GenericResponse<>(webSocketParam, CMD.PING_PONG.name());
    }
}

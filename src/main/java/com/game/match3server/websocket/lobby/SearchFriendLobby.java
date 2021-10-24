package com.game.match3server.websocket.lobby;

import com.game.match3server.service.UserService;
import com.game.match3server.web.GenericResponse;
import com.game.match3server.web.RequestParam;
import com.game.match3server.websocket.CMD;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;

public class SearchFriendLobby implements Lobby{
    @Autowired
    UserService userService;
    @Override
    public GenericResponse<?> process(Object var, Principal principal) {
        RequestParam webSocketParam = (RequestParam) GsonHelper.fromJson(var, RequestParam.class);
        return new GenericResponse<>(userService.getUserPageByNick(webSocketParam.getParam()), CMD.SEARCH_FRIEND.name());
    }
}

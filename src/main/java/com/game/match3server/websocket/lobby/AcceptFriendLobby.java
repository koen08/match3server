package com.game.match3server.websocket.lobby;

import com.game.match3server.service.FriendsLobbyService;
import com.game.match3server.web.GenericResponse;
import com.game.match3server.web.RequestParam;
import com.game.match3server.websocket.CMD;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.Principal;

public class AcceptFriendLobby implements Lobby {
    @Autowired
    FriendsLobbyService friendsLobbyService;

    @Override
    public GenericResponse<?> process(Object obj, Principal principal) throws IOException {
        RequestParam webSocketParam = (RequestParam) GsonHelper.fromJson(obj, RequestParam.class);
        friendsLobbyService.acceptFriend(Long.parseLong(webSocketParam.getParam()));
        return new GenericResponse<>(true, CMD.ACCEPT_FRIEND.name());
    }
}

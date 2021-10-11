package com.game.match3server.websocket.lobby;

import com.game.match3server.service.FriendsLobbyService;
import com.game.match3server.web.GenericResponse;
import com.game.match3server.web.ListResponse;
import com.game.match3server.websocket.CMD;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.Principal;

public class AcceptedFriends implements Lobby{
    @Autowired
    FriendsLobbyService friendsLobbyService;
    @Override
    public GenericResponse<?> process(Object obj, Principal principal) throws IOException {
        return new GenericResponse<>(new ListResponse<>(friendsLobbyService.getMyFriends(principal)), CMD.MY_FRIENDS.name());
    }
}

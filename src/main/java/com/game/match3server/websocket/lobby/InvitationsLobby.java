package com.game.match3server.websocket.lobby;

import com.game.match3server.dao.entity.FriendEntity;
import com.game.match3server.service.FriendsLobbyService;
import com.game.match3server.web.ErrorCode;
import com.game.match3server.web.GenericResponse;
import com.game.match3server.web.RequestParam;
import com.game.match3server.web.Status;
import com.game.match3server.websocket.CMD;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public class InvitationsLobby extends AbstractLobby implements Lobby{
    @Autowired
    FriendsLobbyService friendsLobbyService;
    @Override
    public GenericResponse<?> process(Object obj, Principal principal) throws IOException {
        List<FriendEntity> friendEntityList = friendsLobbyService.getUserToById(principal);
        return new GenericResponse<>(friendEntityList, CMD.GET_INVITED.name());
    }
}

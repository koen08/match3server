package com.game.match3server.websocket.lobby;

import com.game.match3server.exception.CommonException;
import com.game.match3server.service.FriendsLobbyService;
import com.game.match3server.web.ErrorCode;
import com.game.match3server.web.GenericResponse;
import com.game.match3server.web.RequestParam;
import com.game.match3server.web.Status;
import com.game.match3server.websocket.CMD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.Principal;

public class AcceptFriendLobby implements Lobby {
    private static final Logger log = LogManager.getLogger(FriendLobbyInvite.class);
    @Autowired
    FriendsLobbyService friendsLobbyService;

    @Override
    public GenericResponse<?> process(Object obj, Principal principal) throws IOException {
        try {
            RequestParam webSocketParam = (RequestParam) GsonHelper.fromJson(obj, RequestParam.class);
            friendsLobbyService.acceptFriend(Long.parseLong(webSocketParam.getParam()));
        } catch (CommonException e){
            log.warn("Error request: {}", e.getMessage());
            return new GenericResponse<>(new Status((int) e.getCode(), e.getMessage()), CMD.INVITE_FRIEND.name());
        }
        return new GenericResponse<>(true, CMD.ACCEPT_FRIEND.name());
    }
}

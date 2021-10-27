package com.game.match3server.websocket.lobby;

import com.game.match3server.dao.entity.FriendEntity;
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

public class DeleteInviteFriend implements Lobby {
    private static final Logger log = LogManager.getLogger(FriendLobbyInvite.class);
    @Autowired
    FriendsLobbyService friendsLobbyService;

    @Override
    public GenericResponse<?> process(Object obj, Principal principal) throws IOException, CommonException {
        RequestParam webSocketParam = (RequestParam) GsonHelper.fromJson(obj, RequestParam.class);
        FriendEntity friendEntity = friendsLobbyService.getInviteFriendById(Long.parseLong(webSocketParam.getParam()));
        if (friendEntity == null) {
            String msgError = "Friend invite not found, id = " + webSocketParam.getParam();
            log.warn("Error request: {}", msgError);
            return new GenericResponse<>(new Status(ErrorCode.BAD_REQUEST, msgError), CMD.DELETE_INVITE.name());
        }
        friendsLobbyService.deleteInvite(friendEntity);
        return new GenericResponse<>(true, CMD.DELETE_INVITE.name());
    }
}

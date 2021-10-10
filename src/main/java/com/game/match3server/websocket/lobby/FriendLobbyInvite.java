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

public class FriendLobbyInvite extends AbstractLobby implements Lobby{
    @Autowired
    FriendsLobbyService friendsLobbyService;
    @Override
    public GenericResponse<?> process(Object obj, Principal principal) throws IOException {
        RequestParam webSocketParam = (RequestParam) GsonHelper.fromJson(obj, RequestParam.class);
        FriendEntity friendEntity = friendsLobbyService.invite(webSocketParam.getParam(), principal);
        if (friendEntity == null){
            return new GenericResponse<>(new Status(ErrorCode.REPEAT_DATA, "The invitation has already been sent"), CMD.INVITE_FRIEND.name());
        }
        sendMsg(webSocketParam.getParam(), new GenericResponse<>(friendEntity));
        return new GenericResponse<>(true, CMD.INVITE_FRIEND.name());
    }
}

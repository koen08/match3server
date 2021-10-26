package com.game.match3server.websocket.lobby;

import com.game.match3server.dao.entity.FriendEntity;
import com.game.match3server.dao.entity.FriendsEntity;
import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.service.FriendsLobbyService;
import com.game.match3server.service.UserService;
import com.game.match3server.web.GenericResponse;
import com.game.match3server.web.ListResponse;
import com.game.match3server.web.UserPage;
import com.game.match3server.websocket.CMD;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AcceptedFriends implements Lobby{
    @Autowired
    FriendsLobbyService friendsLobbyService;
    @Autowired
    UserService userService;
    @Override
    public GenericResponse<?> process(Object obj, Principal principal) throws IOException {
        List<UserPage> friendEntities = friendsLobbyService.getMyFriends(principal);
        return new GenericResponse<>(friendEntities, CMD.MY_FRIENDS.name());
    }
}

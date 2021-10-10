package com.game.match3server.websocket.lobby;

import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.dao.entity.UserProfile;
import com.game.match3server.service.UserService;
import com.game.match3server.web.GenericResponse;
import com.game.match3server.web.UserProfileDto;
import com.game.match3server.websocket.CMD;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Principal;

public class EnterLobby implements Lobby {
    @Autowired
    UserService userService;

    @Override
    public GenericResponse<UserProfileDto> process(Object obj, Principal principal) {
        return new GenericResponse<>(userService.getUserProfileByNickNameWithAuth(principal), CMD.ENTER_LOBBY.name());
    }
}

package com.game.match3server.websocket.lobby;

import com.game.match3server.web.GenericResponse;

import java.io.IOException;
import java.security.Principal;

public interface Lobby{
    GenericResponse<?> process(Object obj, Principal principal) throws IOException;
}

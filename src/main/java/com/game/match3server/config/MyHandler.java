package com.game.match3server.config;

import com.game.match3server.dao.UserServiceDao;
import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.dao.entity.UserProfile;
import com.game.match3server.service.UserService;
import com.game.match3server.web.CMD;
import com.game.match3server.web.CmdDto;
import com.game.match3server.web.controller.UserController;
import com.google.gson.Gson;
import org.apache.catalina.core.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNullApi;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyHandler implements WebSocketHandler {
    private static final Logger log = LogManager.getLogger(MyHandler.class);
    Map<String, WebSocketSession> webSocketSessionMap = new HashMap<>();
    Gson gson;
    @Autowired
    UserService userService;
    @Autowired
    UserServiceDao userServiceDao;

    public MyHandler(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        webSocketSessionMap.put(Objects.requireNonNull(webSocketSession.getPrincipal()).getName(), webSocketSession);
        log.info("User was connected: {}", webSocketSession.getPrincipal().getName());
      //  SecurityContextHolder.getContext().setAuthentication((Authentication) webSocketSession.getPrincipal());
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        log.info("handleMessage: {}", webSocketMessage.getPayload());
        CmdDto cmdDto = gson.fromJson(webSocketMessage.getPayload().toString(), CmdDto.class);
        if (cmdDto.getCmd().equals(CMD.ENTER_LOBBY.toString())) {
            UserEntity userEntity = userServiceDao.findByLogin(webSocketSession.getPrincipal().getName());
            webSocketSession.sendMessage(new TextMessage(gson.toJson(userService.getUserProfile(userEntity.getNickName()))));
        }
        if (cmdDto.getCmd().equals(CMD.MESSAGE.toString())) {
            WebSocketSession adresat = webSocketSessionMap.get(cmdDto.getEmailTo());
            adresat.sendMessage(new TextMessage(cmdDto.getMsg()));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

package com.game.match3server.websocket;

import com.game.match3server.web.RequestDto;
import com.game.match3server.websocket.lobby.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.sql.Delete;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MyHandler extends AbstractLobby implements WebSocketHandler {
    private static final Logger log = LogManager.getLogger(MyHandler.class);

    public MyHandler(EnterLobby enterLobby, SearchFriendLobby searchFriendLobby, FriendLobbyInvite friendLobbyInvite,
                     InvitationsLobby invitationsLobby, PingPongLobby pingPongLobby, AcceptFriendLobby acceptFriendLobby,
                     AcceptedFriends acceptedFriends, DeleteInviteFriend deleteInviteFriend) {
        register(CMD.ENTER_LOBBY, enterLobby);
        register(CMD.SEARCH_FRIEND, searchFriendLobby);
        register(CMD.INVITE_FRIEND, friendLobbyInvite);
        register(CMD.GET_INVITED, invitationsLobby);
        register(CMD.PING_PONG, pingPongLobby);
        register(CMD.ACCEPT_FRIEND, acceptFriendLobby);
        register(CMD.MY_FRIENDS, acceptedFriends);
        register(CMD.DELETE_INVITE, deleteInviteFriend);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        put(Objects.requireNonNull(webSocketSession.getPrincipal()).getName(), webSocketSession);
        log.info("User was connected: {}", webSocketSession.getPrincipal().getName());
        log.info("Count online user: {}", getOnlineUserSize());
        //  SecurityContextHolder.getContext().setAuthentication((Authentication) webSocketSession.getPrincipal());
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        log.info("handleMessage: {}", webSocketMessage.getPayload());
        RequestDto<?> cmdDto = (RequestDto<?>) GsonHelper.fromJson(webSocketMessage.getPayload(), RequestDto.class);
        Lobby lobby = (Lobby) getObject(CMD.valueOf(cmdDto.getCmd()));
        webSocketSession.sendMessage(new TextMessage(GsonHelper.toJson(lobby.process(cmdDto.getData(), webSocketSession.getPrincipal()))));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        log.error("webSessionError: {}", webSocketSession);
        log.error(throwable.getCause());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        remove(Objects.requireNonNull(webSocketSession.getPrincipal()).getName());
        log.info("User was disconnected: {}", webSocketSession.getPrincipal().getName());
        log.error("User webSession disconnected: {}", webSocketSession);
        log.info("Reason: {}",closeStatus.getReason());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

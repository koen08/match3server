package com.game.match3server.config;

import com.game.match3server.websocket.MyHandler;
import com.game.match3server.websocket.lobby.EnterLobby;
import com.game.match3server.websocket.lobby.FriendLobbyInvite;
import com.game.match3server.websocket.lobby.InvitationsLobby;
import com.game.match3server.websocket.lobby.SearchFriendLobby;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/lobby");
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MyHandler(enterLobby(), searchFriendLobby(), friendLobbyInvite(), invitationsLobby());
    }

    @Bean
    public EnterLobby enterLobby() {
        return new EnterLobby();
    }

    @Bean
    public SearchFriendLobby searchFriendLobby() {
        return new SearchFriendLobby();
    }

    @Bean
    public FriendLobbyInvite friendLobbyInvite() {
        return new FriendLobbyInvite();
    }

    @Bean
    public InvitationsLobby invitationsLobby() {
        return new InvitationsLobby();
    }
}


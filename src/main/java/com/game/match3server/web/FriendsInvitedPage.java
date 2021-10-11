package com.game.match3server.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FriendsInvitedPage {
    private long id;
    private String nickname;
    private String userId;
}

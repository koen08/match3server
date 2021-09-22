package com.game.match3server.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTopBar {
    private String id;
    private String nickname;
    private long coin;
    private long gems;
}

package com.game.match3server.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthUserDto {
    private String authToken;
    private String refreshToken;
}

package com.game.match3server.web;

import com.game.match3server.dao.entity.SpaceshipInfo;
import com.game.match3server.dao.entity.TowerUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserProfileDto {
    private String id;
    private String email;
    private String nickname;
    private int coin;
    private int gems;
    private String spaceShipActiveId;
    private int lastLevel;
    private int rating;
    private List<TowerUser> towers;
    private List<SpaceshipInfo> spaceships;
}

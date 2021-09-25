package com.game.match3server.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SpaceshipInfo {
    private String id;
    private int level;
    private int health;
    private String[] towers;
}

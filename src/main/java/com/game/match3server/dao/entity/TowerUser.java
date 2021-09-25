package com.game.match3server.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@Data
public class TowerUser {
    private String id;
    private int level;
    private int damage;
    private float timeReload;
}

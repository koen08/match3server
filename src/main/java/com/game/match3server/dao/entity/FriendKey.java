package com.game.match3server.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendKey implements Serializable {
    @Column(name = "user_entity_id")
    String friend_one;

    @Column(name = "friends_id")
    String friend_two;
}

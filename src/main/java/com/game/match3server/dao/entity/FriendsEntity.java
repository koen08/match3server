package com.game.match3server.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "auth_user_friends")
@NoArgsConstructor
@AllArgsConstructor
public class FriendsEntity {
    @EmbeddedId
    FriendKey id;
    @ManyToOne
    @MapsId("user_entity_id")
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;
    @ManyToOne
    @MapsId("friends_id")
    @JoinColumn(name = "friends_id")
    private UserEntity userEntity1;
}

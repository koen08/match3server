package com.game.match3server.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "friend_invite")
@Data
@NoArgsConstructor
public class FriendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "user_from")
    String userFrom;
    @Column(name = "user_to")
    String userTo;
    public FriendEntity(String userFrom, String userTo) {
        this.userFrom = userFrom;
        this.userTo = userTo;
    }
}

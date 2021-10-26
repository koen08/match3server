package com.game.match3server.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "auth_user")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    private String id;
    @Column
    private String login;
    @Column
    private String password;
    @Column(name = "nickname")
    private String nickName;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FriendsEntity> groupStudies = new ArrayList<>();
}

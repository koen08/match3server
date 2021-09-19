package com.game.match3server.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @OneToMany(mappedBy = "roleEntity",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserEntity> userEntityArrayList = new ArrayList<>();
}

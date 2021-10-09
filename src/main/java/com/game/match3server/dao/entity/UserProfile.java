package com.game.match3server.dao.entity;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
)
public class UserProfile {
    @Id
    private String id;
    @Column
    private int coin;
    @Column
    private int gems;
    @Column(name = "spaceship_active_id")
    private String spaceShipActiveId;
    @Column(name = "last_level")
    private int lastLevel;
    @Column
    private int rating;
    @Column(name = "towers", columnDefinition = "text[]")
    @Type(type = "string-array")
    private String[] towers;
    @Column(name = "spaceships", columnDefinition = "text[]")
    @Type(type = "string-array")
    private String[] spaceships;
    @Column(name = "friends", columnDefinition = "text[]")
    @Type(type = "string-array")
    private String[] friends;
}

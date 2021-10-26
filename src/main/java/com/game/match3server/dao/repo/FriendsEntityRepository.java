package com.game.match3server.dao.repo;

import com.game.match3server.dao.entity.FriendEntity;
import com.game.match3server.dao.entity.FriendKey;
import com.game.match3server.dao.entity.FriendsEntity;
import com.game.match3server.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendsEntityRepository extends JpaRepository<FriendsEntity, FriendKey> {
    List<FriendsEntity> findALlByUserEntityOrUserEntity1(UserEntity userEntity, UserEntity userEntity1);
}

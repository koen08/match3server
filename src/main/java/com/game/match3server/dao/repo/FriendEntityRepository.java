package com.game.match3server.dao.repo;

import com.game.match3server.dao.entity.FriendEntity;
import com.game.match3server.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FriendEntityRepository extends JpaRepository<FriendEntity, Long> {
    FriendEntity findByUserFromAndUserTo(String userFrom,String userTo);
    Optional<FriendEntity> findById(Long id);
    List<FriendEntity> findAllByUserTo(String userTo);
    List<FriendEntity> findAllByUserToOrUserFrom(String id, String id2);
}

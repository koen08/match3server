package com.game.match3server.dao.repo;

import com.game.match3server.dao.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendEntityRepository extends JpaRepository<FriendEntity, Long> {
    FriendEntity findByUserFromAndUserTo(String userFrom,String userTo);
    List<FriendEntity> findAllByUserTo(String userTo);
}

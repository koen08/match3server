package com.game.match3server.dao.repo;


import com.game.match3server.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByLogin(String login);
}

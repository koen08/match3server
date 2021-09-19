package com.game.match3server.dao.repo;
import com.game.match3server.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByName(String name);
}
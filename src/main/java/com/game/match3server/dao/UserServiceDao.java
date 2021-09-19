package com.game.match3server.dao;

import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.dao.repo.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceDao {
    private final UserEntityRepository userEntityRepository;

    public UserServiceDao(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public UserEntity saveUser(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    public UserEntity findByLogin(String login) {
        return userEntityRepository.findByLogin(login);
    }

    public UserEntity findById(String id) {
        return userEntityRepository.findById(id).get();
    }

    public void removeUser(String id) {
        userEntityRepository.deleteById(id);
    }
}

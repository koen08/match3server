package com.game.match3server.dao;

import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.dao.repo.UserEntityRepository;
import com.game.match3server.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceDao {
    private final UserEntityRepository userEntityRepository;

    public UserServiceDao(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public void saveUser(UserEntity userEntity) {
        userEntityRepository.save(userEntity);
    }

    public UserEntity findByLogin(String login) {
        return userEntityRepository.findByLogin(login);
    }

    public UserEntity getByNickname(String nickname) {
        return userEntityRepository.findByNickName(nickname);
    }

    public UserEntity findById(String id) {
        return userEntityRepository.findById(id).get();
    }

    public void removeUser(String id) {
        userEntityRepository.deleteById(id);
    }
    public void removeAll(){
        userEntityRepository.deleteAll();
    }

    public List<UserEntity> getAllUser(){
        return userEntityRepository.findAll();
    }

    public UserEntity getUserByJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return findByLogin(userDetails.getUsername());
    }
}

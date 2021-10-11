package com.game.match3server.dao;

import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.dao.entity.UserProfile;
import com.game.match3server.dao.repo.UserProfileRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileDao {
    private static final Logger log = LogManager.getLogger(UserServiceDao.class);
    private final UserProfileRepository userProfileRepository;
    private final UserServiceDao userServiceDao;

    public UserProfileDao(UserProfileRepository userProfileRepository, UserServiceDao userServiceDao) {
        this.userProfileRepository = userProfileRepository;
        this.userServiceDao = userServiceDao;
    }

    public void save(UserProfile userProfile){
        userProfileRepository.save(userProfile);
    }
    public UserProfile getByUserId(String id){
        log.info("getByUserId start: {}", id);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(id);
        UserProfile userProfile = null;
        if (userProfileOptional.isPresent()){
            userProfile = userProfileOptional.get();
        } else {
            log.warn("getByUserId: {}", userProfileOptional);
        }
        log.info("getByUserId finish: {}", userProfile);
        return userProfile;
    }
    public UserProfile getByNickname(String nickname){
        UserEntity userEntity = userServiceDao.getByNickname(nickname);
        return getByUserId(userEntity.getId());
    }
    public void removeAll(){
        userProfileRepository.deleteAll();
    }
}

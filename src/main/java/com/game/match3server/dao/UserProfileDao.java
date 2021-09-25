package com.game.match3server.dao;

import com.game.match3server.dao.entity.UserProfile;
import com.game.match3server.dao.repo.UserProfileRepository;
import com.game.match3server.service.AuthorizationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileDao {
    private static final Logger log = LogManager.getLogger(UserServiceDao.class);
    private final UserProfileRepository userProfileRepository;

    public UserProfileDao(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
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
    public void removeAll(){
        userProfileRepository.deleteAll();
    }
}

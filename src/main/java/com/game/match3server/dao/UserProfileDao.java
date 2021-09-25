package com.game.match3server.dao;

import com.game.match3server.dao.entity.UserProfile;
import com.game.match3server.dao.repo.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileDao {
    private final UserProfileRepository userProfileRepository;

    public UserProfileDao(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public void save(UserProfile userProfile){
        userProfileRepository.save(userProfile);
    }
    public UserProfile getByUserId(String id){
        Optional<UserProfile> userProfile = userProfileRepository.findById(id);
        return userProfile.get();
    }
    public void removeAll(){
        userProfileRepository.deleteAll();
    }
}

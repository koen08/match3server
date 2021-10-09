package com.game.match3server.service;

import com.game.match3server.dao.UserProfileDao;
import com.game.match3server.dao.UserServiceDao;
import com.game.match3server.dao.entity.SpaceshipInfo;
import com.game.match3server.dao.entity.TowerUser;
import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.dao.entity.UserProfile;
import com.game.match3server.web.UserPage;
import com.game.match3server.web.UserProfileDto;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    final UserServiceDao userServiceDao;
    final UserProfileDao userProfileDao;

    public UserService(UserServiceDao userServiceDao, UserProfileDao userProfileDao) {
        this.userServiceDao = userServiceDao;
        this.userProfileDao = userProfileDao;
    }

    public UserProfileDto getUserProfile(String nickname) {
        return getUserProfile(userServiceDao.getByNickname(nickname));
    }

    public UserPage getUserPage(String nickname) {
        UserEntity userEntity = userServiceDao.getByNickname(nickname);
        if (userEntity == null){
            return null;
        }
        return new UserPage(userEntity.getLogin(), userEntity.getNickName());
    }

    public UserProfileDto getUserProfile() {
        return getUserProfile(userServiceDao.getUserByJwt());
    }

    public UserProfileDto getUserProfileByNickNameWithAuth(Principal principal) {
        UserEntity userEntity = userServiceDao.findByLogin(principal.getName());
        return getUserProfile(userEntity.getNickName());
    }

    private UserProfileDto getUserProfile(UserEntity userEntity) {
        UserProfile userProfile = userProfileDao.getByUserId(userEntity.getId());
        return new UserProfileDto(
                userEntity.getId(), userEntity.getLogin(), userEntity.getNickName(), userProfile.getCoin(), userProfile.getGems(), userProfile.getSpaceShipActiveId(),
                userProfile.getLastLevel(), userProfile.getRating(), createTowerList(userProfile.getTowers()),
                createSpaceShipInfo(userProfile.getSpaceships())
        );
    }

    private List<TowerUser> createTowerList(String[] towers) {
        Gson gson = new Gson();
        List<TowerUser> towerUsers = new ArrayList<>();
        for (String tower : towers) {
            TowerUser towerUser = gson.fromJson(tower, TowerUser.class);
            towerUsers.add(towerUser);
        }
        return towerUsers;
    }

    private List<SpaceshipInfo> createSpaceShipInfo(String[] spaceships) {
        Gson gson = new Gson();
        List<SpaceshipInfo> spaceshipInfos = new ArrayList<>();
        for (String spaceship : spaceships) {
            SpaceshipInfo spaceshipInfo = gson.fromJson(spaceship, SpaceshipInfo.class);
            spaceshipInfos.add(spaceshipInfo);
        }
        return spaceshipInfos;
    }
}

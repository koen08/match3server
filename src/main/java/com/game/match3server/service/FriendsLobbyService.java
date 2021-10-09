package com.game.match3server.service;

import com.game.match3server.dao.FriendServiceDao;
import com.game.match3server.dao.UserProfileDao;
import com.game.match3server.dao.UserServiceDao;
import com.game.match3server.dao.entity.FriendEntity;
import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.web.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class FriendsLobbyService {
    @Autowired
    UserServiceDao userServiceDao;
    @Autowired
    FriendServiceDao friendServiceDao;

    public FriendEntity invite(String login, Principal principal) { //Если какое то исключение происходит то все вылетает
        UserEntity userEntity = userServiceDao.findByLogin(principal.getName());
        UserEntity friend = userServiceDao.findByLogin(login);
        if (friendServiceDao.findByUserToId(userEntity.getId(), friend.getId()) != null) {
            return null;
        }
        return friendServiceDao.save(new FriendEntity(userEntity.getId(), friend.getId()));
    }

    public List<FriendEntity> getUserToById(Principal principal) {
        UserEntity userEntity = userServiceDao.findByLogin(principal.getName());
        return friendServiceDao.getListInvited(userEntity.getId());
    }

}

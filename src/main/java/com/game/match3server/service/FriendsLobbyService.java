package com.game.match3server.service;

import com.game.match3server.dao.FriendServiceDao;
import com.game.match3server.dao.UserProfileDao;
import com.game.match3server.dao.UserServiceDao;
import com.game.match3server.dao.entity.FriendEntity;
import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.dao.entity.UserProfile;
import com.game.match3server.web.ListResponse;
import com.game.match3server.web.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Service
public class FriendsLobbyService {
    @Autowired
    UserServiceDao userServiceDao;
    @Autowired
    FriendServiceDao friendServiceDao;
    @Autowired
    UserProfileDao userProfileDao;
    @Autowired
    UserService userService;

    public FriendEntity invite(String login, Principal principal) { //Если какое то исключение происходит то все вылетает
        UserEntity userEntity = userServiceDao.findByLogin(principal.getName());
        UserEntity friend = userServiceDao.findByLogin(login);
        if (friendServiceDao.findByUserToId(userEntity.getNickName(), friend.getNickName()) != null) {
            return null;
        }
        return friendServiceDao.save(new FriendEntity(userEntity.getNickName(), friend.getNickName()));
    }

    public List<FriendEntity> getUserToById(Principal principal) {
        UserEntity userEntity = userServiceDao.findByLogin(principal.getName());
        return friendServiceDao.getListInvited(userEntity.getId());
    }

    public List<String> getMyFriends(Principal principal){
        UserEntity userEntity = userService.getByPrincipal(principal);
        return Arrays.asList(userProfileDao.getByUserId(userEntity.getId()).getFriends());
    }

    public void acceptFriend(long inviteId) {
        FriendEntity friendEntity = friendServiceDao.getById(inviteId);
        UserProfile my = userProfileDao.getByNickname(friendEntity.getUserTo());
        UserProfile friend = userProfileDao.getByNickname(friendEntity.getUserFrom());
        List<String> myFriends = Arrays.asList(my.getFriends());
        myFriends.add(friend.getId());
        my.setFriends(myFriends.toArray(new String[0]));
        myFriends = Arrays.asList(friend.getFriends());
        myFriends.add(my.getId());
        friend.setFriends(myFriends.toArray(new String[0]));
        userProfileDao.save(my);
        userProfileDao.save(friend);
    }

}

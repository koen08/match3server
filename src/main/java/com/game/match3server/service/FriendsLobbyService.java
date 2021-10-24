package com.game.match3server.service;

import com.game.match3server.dao.FriendServiceDao;
import com.game.match3server.dao.UserProfileDao;
import com.game.match3server.dao.UserServiceDao;
import com.game.match3server.dao.entity.FriendEntity;
import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.dao.entity.UserProfile;
import com.game.match3server.web.ListResponse;
import com.game.match3server.web.UserPage;
import com.game.match3server.web.UserPageInvited;
import com.game.match3server.web.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FriendsLobbyService {
    @Autowired
    UserServiceDao userServiceDao;
    @Autowired
    FriendServiceDao friendServiceDao;
    @Autowired
    UserService userService;

    public FriendEntity invite(String id, Principal principal) { //Если какое то исключение происходит то все вылетает
        if (friendServiceDao.findByUserToId(principal.getName(), id) != null) {
            return null;
        }
        return friendServiceDao.save(new FriendEntity(principal.getName(), id));
    }

    public List<UserPageInvited> getUserToById(Principal principal) {
        List<FriendEntity> friendEntityList = friendServiceDao.getListInvited(principal.getName());
        List<UserPageInvited> userPageInviteds = new ArrayList<>();
        for (FriendEntity friendEntity : friendEntityList) {
            UserEntity user = userService.getUserEntityById(friendEntity.getUserFrom());
            userPageInviteds.add(new UserPageInvited(friendEntity.getId(), new UserPage(user.getId(), user.getNickName())));
        }
        return userPageInviteds;
    }

    public List<String> getMyFriends(Principal principal) {
    /*    UserEntity userEntity = userService.getByPrincipal(principal);
        return Arrays.asList(userProfileDao.getByUserId(userEntity.getId()).getFriends());*/
        return null;
    }

    public void acceptFriend(long inviteId) {
        /*FriendEntity friendEntity = friendServiceDao.getById(inviteId);
        UserProfile my = userProfileDao.getByNickname(friendEntity.getUserTo());
        UserProfile friend = userProfileDao.getByNickname(friendEntity.getUserFrom());
        List<String> myFriends = Arrays.asList(my.getFriends());
        myFriends.add(friend.getId());
        my.setFriends(myFriends.toArray(new String[0]));
        myFriends = Arrays.asList(friend.getFriends());
        myFriends.add(my.getId());
        friend.setFriends(myFriends.toArray(new String[0]));
        userProfileDao.save(my);
        userProfileDao.save(friend);*/
    }

}

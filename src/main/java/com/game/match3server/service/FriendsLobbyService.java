package com.game.match3server.service;

import com.game.match3server.dao.FriendServiceDao;
import com.game.match3server.dao.UserProfileDao;
import com.game.match3server.dao.UserServiceDao;
import com.game.match3server.dao.entity.*;
import com.game.match3server.exception.CommonException;
import com.game.match3server.web.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

    public void deleteInvite(FriendEntity friendEntity) {
        friendServiceDao.delete(friendEntity);
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

    public List<UserPage> getMyFriends(Principal principal) {
        return getMyFriends(principal.getName());
    }

    public List<UserPage> getMyFriends(String id) {
        UserEntity userEntity = userServiceDao.findById(id);
        List<FriendsEntity> friendsEntities = friendServiceDao.getSetFriends(userEntity);
        List<UserPage> userPages = new ArrayList<>();
        for (FriendsEntity friendsEntity : friendsEntities) {
            if (!friendsEntity.getUserEntity().getId().equals(userEntity.getId())){
                userPages.add(new UserPage(friendsEntity.getUserEntity().getId(), friendsEntity.getUserEntity().getNickName()));
            } else {
                userPages.add(new UserPage(friendsEntity.getUserEntity1().getId(), friendsEntity.getUserEntity1().getNickName()));
            }
        }
        return userPages;
    }

    public boolean isFriends(UserEntity userEntity, UserEntity userEntity1){
        List<FriendsEntity> friendsEntities = friendServiceDao.getSetFriends(userEntity, userEntity1);
        return friendsEntities.size() > 0;
    }

    @Transactional
    public void acceptFriend(Long inviteId) throws CommonException { // Переписать!
        FriendEntity friendEntity = friendServiceDao.getById(inviteId);
        if (friendEntity == null) {
            throw new CommonException("inviteId not found", ErrorCode.BAD_REQUEST);
        }
        UserEntity userEntity = userService.getUserEntityById(friendEntity.getUserTo());
        UserEntity friend = userService.getUserEntityById(friendEntity.getUserFrom());
        FriendsEntity friendsEntity = new FriendsEntity(new FriendKey(userEntity.getId(), friend.getId()), userEntity, friend);
        friendServiceDao.save(friendsEntity);
        deleteInvite(friendEntity);
    }

}

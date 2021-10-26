package com.game.match3server.dao;

import com.game.match3server.dao.entity.FriendEntity;
import com.game.match3server.dao.entity.FriendsEntity;
import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.dao.repo.FriendEntityRepository;
import com.game.match3server.dao.repo.FriendsEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FriendServiceDao {
    @Autowired
    FriendEntityRepository friendEntityRepository;
    @Autowired
    FriendsEntityRepository friendsEntityRepository;
    @Autowired
    UserServiceDao userServiceDao;
    public FriendEntity save(FriendEntity friendEntity){
        return friendEntityRepository.save(friendEntity);
    }
    public FriendEntity findByUserToId(String userFrom,String userTo){
        return friendEntityRepository.findByUserFromAndUserTo(userFrom, userTo);
    }
    public List<FriendEntity> getListInvited(String userId){
        return friendEntityRepository.findAllByUserTo(userId);
    }
    public boolean checkInvited(String userId){
        return friendEntityRepository.findAllByUserTo(userId) != null;
    }
    public FriendEntity getById(long id){
        Optional<FriendEntity> friendEntity = friendEntityRepository.findById(id);
        return friendEntity.orElse(null);
    }
    public void delete(FriendEntity friendEntity){
        friendEntityRepository.delete(friendEntity);
    }
    public List<FriendsEntity> getSetFriends(UserEntity userEntity){
       return friendsEntityRepository.findALlByUserEntityOrUserEntity1(userEntity, userEntity);
    }

    public List<FriendsEntity> getSetFriends(UserEntity userEntity, UserEntity userEntity1){
        return friendsEntityRepository.findALlByUserEntityOrUserEntity1(userEntity, userEntity1);
    }
    public void save(FriendsEntity friendsEntity){
        friendsEntityRepository.save(friendsEntity);
    }
}

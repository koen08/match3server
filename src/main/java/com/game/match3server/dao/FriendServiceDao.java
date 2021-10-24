package com.game.match3server.dao;

import com.game.match3server.dao.entity.FriendEntity;
import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.dao.repo.FriendEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceDao {
    @Autowired
    FriendEntityRepository friendEntityRepository;
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
        return friendEntityRepository.getById(id);
    }
}

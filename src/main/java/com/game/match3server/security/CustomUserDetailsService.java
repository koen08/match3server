package com.game.match3server.security;

import com.game.match3server.dao.UserServiceDao;
import com.game.match3server.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserServiceDao userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findById(username);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}

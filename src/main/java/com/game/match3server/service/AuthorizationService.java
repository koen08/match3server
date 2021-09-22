package com.game.match3server.service;

import com.game.match3server.dao.UserServiceDao;
import com.game.match3server.dao.entity.RoleEntity;
import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.dao.repo.RoleEntityRepository;
import com.game.match3server.exception.CommonException;
import com.game.match3server.security.jwt.JwtProvider;
import com.game.match3server.web.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class AuthorizationService {
    @Autowired
    UserServiceDao userServiceDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleEntityRepository roleEntityRepository;
    @Autowired
    JwtProvider jwtProvider;

    public AuthUserDto createAuthToken(String authorization) throws CommonException {
        authorization = cutAuthCode(authorization);
        Map<String, String> loginAndPasswordMap = getLoginAndPassword(authorization);
        String login = loginAndPasswordMap.get("login");
        String password = loginAndPasswordMap.get("password");
        UserEntity userEntity = userServiceDao.findByLogin(login);
        if (userEntity == null) throw new CommonException("Email don't found", ErrorCode.UNAUTHORIZED);
        else if (!checkPasswordUser(password, userEntity)){
            throw new CommonException("Invalid username or password", ErrorCode.UNAUTHORIZED);
        }
        return new AuthUserDto(jwtProvider.generateToken(userEntity.getLogin()), null,
                new UserTopBar(userEntity.getId(), userEntity.getNickName(), new Random().nextInt(1000), new Random().nextInt(1000)));
    }

    private String cutAuthCode(String authorization) throws CommonException {
        if (authorization.startsWith("Basic")) {
            authorization = authorization.substring(6);
            if (authorization.isEmpty()) {
                throw new CommonException("Empty authorization data", ErrorCode.BAD_REQUEST);
            }
        } else {
            throw new CommonException("Authorization must start with Basic", ErrorCode.BAD_REQUEST);
        }
        return authorization;
    }

    private Map<String, String> getLoginAndPassword(String authHeader) throws BadCredentialsException {
        String customerCredentials = (convertFromBase64(authHeader));
        String login = customerCredentials.split(":")[0];
        String password = customerCredentials.split(":")[1];
        Map<String, String> loginAndPasswordMap = new HashMap<>();
        loginAndPasswordMap.put("login", login);
        loginAndPasswordMap.put("password", password);
        return loginAndPasswordMap;
    }

    private Boolean checkPasswordUser(String password, UserEntity userEntity) throws CommonException {
        if (passwordEncoder.matches(password, userEntity.getPassword())) {
            return true;
        } else return false;
    }

    private String convertFromBase64(String base64String) {
        byte[] bytes = base64String.getBytes(StandardCharsets.UTF_8);
        bytes = Base64.getDecoder().decode(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public AnswerResponse createPerson(AuthDto authDto) throws CommonException {
        if (userServiceDao.findByLogin(authDto.getLogin()) == null) {
            UserEntity userEntity = new UserEntity();
            RoleEntity userRole = roleEntityRepository.findByName("ROLE_" + authDto.getRole());
            userEntity.setRoleEntity(userRole);
            userEntity.setLogin(authDto.getLogin());
            userEntity.setPassword(authDto.getPassword());
            userEntity.setNickName(authDto.getNickName());
            userEntity.setId(UUID.randomUUID().toString() + System.currentTimeMillis());
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userServiceDao.saveUser(userEntity);
        } else throw new CommonException("The user with this email exists", ErrorCode.REPEAT_DATA);
        return new AnswerResponse("Registration was successful");
    }
}

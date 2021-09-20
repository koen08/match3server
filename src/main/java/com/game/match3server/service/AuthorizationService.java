package com.game.match3server.service;

import com.game.match3server.dao.UserServiceDao;
import com.game.match3server.dao.entity.RoleEntity;
import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.dao.repo.RoleEntityRepository;
import com.game.match3server.exception.CommonException;
import com.game.match3server.security.jwt.JwtProvider;
import com.game.match3server.web.AnswerResponse;
import com.game.match3server.web.AuthDto;
import com.game.match3server.web.Token;
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

    public Token createAuthToken(String authorization) throws CommonException {
        authorization = cutAuthCode(authorization);
        Map<String, String> loginAndPasswordMap = getLoginAndPassword(authorization);
        String login = loginAndPasswordMap.get("login");
        String password = loginAndPasswordMap.get("password");
        UserEntity userEntity = userServiceDao.findByLogin(login);
        if (userEntity == null) throw new CommonException("Пользователь с таким Email не существует");
        else if (!checkPasswordUser(password, userEntity)) throw new CommonException("Неверный логин или пароль");
        return new Token(jwtProvider.generateToken(userEntity.getLogin()), null);
    }

    private String cutAuthCode(String authorization) throws CommonException {
        if (authorization.startsWith("Basic")) {
            authorization = authorization.substring(6);
            if (authorization.isEmpty()) {
                throw new CommonException("Пустые данные авторизации");
            }
        } else {
            throw new CommonException("Пустые данные авторизации");
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
            userEntity.setId(UUID.randomUUID().toString() + new Date());
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userServiceDao.saveUser(userEntity);
        } else throw new CommonException("Пользователь с данной электронной почтой существует");
        return new AnswerResponse("Регистрация прошла успешно");
    }
}

package com.game.match3server.service;

import com.game.match3server.dao.UserProfileDao;
import com.game.match3server.dao.UserServiceDao;
import com.game.match3server.dao.entity.*;
import com.game.match3server.dao.repo.RoleEntityRepository;
import com.game.match3server.exception.CommonException;
import com.game.match3server.security.jwt.JwtProvider;
import com.game.match3server.web.*;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class AuthorizationService {
    private static final Logger log = LogManager.getLogger(AuthorizationService.class);
    @Autowired
    UserServiceDao userServiceDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleEntityRepository roleEntityRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserProfileDao userProfileDao;

    public Object createAuthToken(String authorization) throws CommonException {
        log.info("createAuthToken: {}", authorization);
        authorization = cutAuthCode(authorization);
        Map<String, String> loginAndPasswordMap = getLoginAndPassword(authorization);
        String login = loginAndPasswordMap.get("login");
        String password = loginAndPasswordMap.get("password");
        UserEntity userEntity = userServiceDao.findByLogin(login);
        if (userEntity == null) {
            log.warn("Email don't found");
            return new Status(ErrorCode.UNAUTHORIZED, "Email don't found");
        } else if (!checkPasswordUser(password, userEntity)) {
            log.warn("Invalid username or password");
            return new Status(ErrorCode.UNAUTHORIZED, "Invalid username or password");
        }
        log.info("createAuthToken: ok");
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

    public Object createPerson(AuthDto authDto) throws CommonException {
        if (userServiceDao.getByNickname(authDto.getNickName()) != null){
            log.warn("The user with this nickname exists");
            return new Status(ErrorCode.REPEAT_DATA, "The user with this nickname exists");
        }
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
            List<String> towers = new ArrayList<>();
            Gson gson = new Gson();
            towers.add(gson.toJson(new TowerUser("Lazer_Twr_Def", 1, 12, (float) 0.45)));
            towers.add(gson.toJson(new TowerUser("Plasma_DefTwr", 1, 15, (float) 0.8)));
            List<String> spaceships = new ArrayList<>();
            List<String> towersShipId = new ArrayList<>();
            towersShipId.add("Lazer_Twr_Def");
            spaceships.add(gson.toJson(new SpaceshipInfo("SpaceShip_1", 1, 150, towersShipId.toArray(new String[0]))));
            UserProfile userProfile = new UserProfile(
                    userEntity.getId(), 100, 25, "SpaceShip_1", 1, 0,
                    towers.toArray(new String[0]), spaceships.toArray(new String[0]));
            userProfileDao.save(userProfile);

        } else {
            log.warn("The user with this email exists");
            return new Status(ErrorCode.REPEAT_DATA, "The user with this email exists");
        }
        log.info("Registration was successful");
        return new AnswerResponse("Registration was successful");
    }
}

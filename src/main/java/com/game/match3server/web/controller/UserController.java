package com.game.match3server.web.controller;

import com.game.match3server.service.AuthorizationService;
import com.game.match3server.service.UserService;
import com.game.match3server.web.GenericResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LogManager.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public ResponseEntity<GenericResponse<?>> getUser() throws AuthException {
        log.info("start request with POST [/user/]");
        return new ResponseEntity<>(new GenericResponse<>(
                userService.getUserProfile()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{nickname}")
    public ResponseEntity<GenericResponse<?>> getUserByNickName(@PathVariable String nickname) throws AuthException {
        return new ResponseEntity<>(new GenericResponse<>(
                userService.getUserProfile(nickname)), HttpStatus.OK);
    }
}

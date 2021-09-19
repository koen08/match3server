package com.game.match3server.web.controller;

import com.game.match3server.exception.CommonException;
import com.game.match3server.service.AuthorizationService;
import com.game.match3server.web.AuthDto;
import com.game.match3server.web.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
public class AuthController {
    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<?>> registerUser(@Valid @RequestBody AuthDto authDto) throws CommonException {
        return new ResponseEntity<>(new GenericResponse<>(
                authorizationService.createPerson(authDto)), HttpStatus.OK);
    }
    /*@PreAuthorize("hasAnyRole('USER', 'COMPANY') and @access.accessUser(principal, #authDto.id)")
    @PutMapping("/user/update")
    public ResponseEntity<GenericResponse<?>> putUser(@Valid @RequestBody AuthDto authDto) throws AuthException {
        return new ResponseEntity<>(new GenericResponse<>(
                authorizationService.updateUser(authDto)), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('USER', 'COMPANY') and @access.accessUser(principal, #id)")
    @GetMapping("/user/{id}")
    public ResponseEntity<GenericResponse<?>> getUser(@PathVariable String id) throws AuthException {
        return new ResponseEntity<>(new GenericResponse<>(
                authorizationService.getUser(id)), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('USER', 'COMPANY') and @access.accessUser(principal, #authDto.id)")
    @DeleteMapping("/user/remove")
    public ResponseEntity<GenericResponse<?>> removeUser(@RequestBody AuthDto authDto) throws AuthException {
        return new ResponseEntity<>(new GenericResponse<>(
                authorizationService.removeUser(authDto)), HttpStatus.OK);
    }*/
    @PostMapping("/login")
    public ResponseEntity<GenericResponse<?>> auth(@NotBlank @RequestHeader("Authorization") String authorizationHeader)
            throws Exception {
        return new ResponseEntity<>(new GenericResponse<>(authorizationService.createAuthToken(authorizationHeader)), HttpStatus.OK);
    }
}

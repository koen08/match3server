package com.game.match3server.web;

public interface ErrorCode {
    long REPEAT_DATA=100;
    long OK = 200;
    long BAD_REQUEST=400;
    long UNAUTHORIZED=401;
    long INTERNAL_SERVER_ERROR=500;
}

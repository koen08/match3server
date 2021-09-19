package com.game.match3server.exception;

public class AccessException extends Exception{
    public AccessException() {
        super("Нет прав доступа");
    }
}

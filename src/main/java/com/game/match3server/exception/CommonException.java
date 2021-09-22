package com.game.match3server.exception;

public class CommonException extends Exception {
    private long code;
    public CommonException(String message, long code) {
        super(message);
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}

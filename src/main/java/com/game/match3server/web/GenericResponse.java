package com.game.match3server.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
    private long code;
    T responseData;

    public GenericResponse(T responseData, long code) {
        this.responseData = responseData;
        this.code = code;
    }

    public GenericResponse(T responseData) {
        this.responseData = responseData;
        this.code = ErrorCode.OK;
    }
}

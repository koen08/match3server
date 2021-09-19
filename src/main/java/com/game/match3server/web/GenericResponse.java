package com.game.match3server.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
    private String errorMessage;
    T responseData;

    public GenericResponse(T responseData) {
        this.responseData = responseData;
    }

    public GenericResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

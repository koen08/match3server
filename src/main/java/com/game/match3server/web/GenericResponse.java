package com.game.match3server.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
    private String cmd;
    private Status status;
    T data;

    public GenericResponse(Status status, String cmd) {
        this.status = status;
    }

    public GenericResponse(Boolean ok, String cmd){
        if (ok){
            this.status = new Status(ErrorCode.OK, "Ok");
        }
        this.cmd = cmd;
    }

    public GenericResponse(T data) {
        if (data instanceof Status){
            Status status = (Status) data;
            this.status = new Status(status.getCode(), status.getMessage());
        } else {
            this.data = data;
            this.status = new Status(ErrorCode.OK, "Ok");
        }
    }

    public GenericResponse(T data, String cmd) {
        if (data instanceof Status){
            Status status = (Status) data;
            this.status = new Status(status.getCode(), status.getMessage());
            this.cmd = cmd;
        } else {
            this.data = data;
            this.status = new Status(ErrorCode.OK, "Ok");
            this.cmd = cmd;
        }
    }
}

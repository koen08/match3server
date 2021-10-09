package com.game.match3server.websocket.lobby;

import com.google.gson.Gson;

public class GsonHelper {
    private static final Gson gson = new Gson();

    public static Object fromJson(Object object, Class<?> clazz) {
        return gson.fromJson(object.toString(), clazz);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}

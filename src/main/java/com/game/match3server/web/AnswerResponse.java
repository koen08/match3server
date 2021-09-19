package com.game.match3server.web;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AnswerResponse {
    Map<String, String> answerResponseMap;
    public AnswerResponse(String answer){
        this.answerResponseMap = new HashMap<>();
        answerResponseMap.put("answer", answer);
    }
}

package com.game.match3server.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/time")
    @ResponseStatus(HttpStatus.OK)
    public String getCurrentTime() {
        return Instant.now().toString();
    }

    @GetMapping("/logs")
    @ResponseStatus(HttpStatus.OK)
    public String getLogs(Model model) {
        List<String> logsList = new LinkedList<>();
        try {
            File file = new File("log.log");
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                // считываем остальные строки в цикле
                line = reader.readLine();
                logsList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        model.addAttribute("logsList", logsList);
        return "logs";
    }
}

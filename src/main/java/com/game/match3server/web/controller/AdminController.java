package com.game.match3server.web.controller;

import com.game.match3server.dao.UserProfileDao;
import com.game.match3server.dao.UserServiceDao;
import com.game.match3server.dao.entity.UserEntity;
import com.game.match3server.web.AuthDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger log = LogManager.getLogger(AdminController.class);
    @Autowired
    UserServiceDao userServiceDao;
    @Autowired
    UserProfileDao userProfileDao;

    @GetMapping("/time")
    @ResponseStatus(HttpStatus.OK)
    public String getCurrentTime() {
        return Instant.now().toString();
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String getIndex() {
        return "index";
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
                // считываем остальные строки в цикле
                line = reader.readLine();
                logsList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        model.addAttribute("logsList", logsList);
        return "logslist";
    }

    @GetMapping("/remove/user/all")
    @ResponseStatus(HttpStatus.OK)
    public String removeAll() {
        log.info("start request with GET [/remove/user/all]");
        userServiceDao.removeAll();
        userProfileDao.removeAll();
        log.info("all user deleted");
        return "removeuser";
    }

    @GetMapping("/user/all")
    @ResponseStatus(HttpStatus.OK)
    public String getAllUser(Model model) {
        log.info("start request with GET [/user/all]");
        List<UserEntity> list = userServiceDao.getAllUser();
        List<AuthDto> authDtos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            AuthDto authDto = new AuthDto();
            authDto.setId(list.get(i).getId());
            authDto.setLogin(list.get(i).getLogin());
            authDto.setNickName(list.get(i).getNickName());
            authDtos.add(authDto);
        }
        model.addAttribute("userList", authDtos);
        return "userlist";
    }

    @RequestMapping(value = "/doc", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void getAllUser(HttpServletResponse response) throws IOException {
        File file = new File("src/main/doc/doc.pdf");
        byte[] data = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
        response.setContentLength(data.length);

        response.getOutputStream().write(data);
        response.getOutputStream().flush();
    }


}

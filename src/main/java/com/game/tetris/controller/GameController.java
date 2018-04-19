package com.game.tetris.controller;

import com.game.tetris.entity.UserEntity;
import com.game.tetris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

/**
 * @author zjb
 * @date 2018/4/19.
 */
@RestController
public class GameController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/score/max")
    @ResponseBody
    public UserEntity getMaxScore(){
        UserEntity userEntity = userService.getMaxScore();
        userEntity.setPassword("");
        userEntity.setToken("");
        return userEntity;
    }

    @GetMapping("/api/user")
    @ResponseBody
    public UserEntity getUser(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        UserEntity userEntity = new UserEntity();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    userEntity = userService.findByToken(token);
                    userEntity.setPassword("");
                    userEntity.setToken("");
                }
            }
        }
        return userEntity;
    }

    @GetMapping("/api/score/{s}")
    @ResponseBody
    public Boolean setScore(@PathVariable("s") Integer s, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        UserEntity userEntity = new UserEntity();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    UserEntity user = userService.findByToken(token);
                    if(user.getMaxScore() < s){
                        userEntity.setToken(token);
                        userEntity.setMaxScore(s);
                        userService.updateScore(userEntity);
                    }
                }
            }
        }
        return true;
    }
}

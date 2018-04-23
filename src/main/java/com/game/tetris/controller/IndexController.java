package com.game.tetris.controller;

import com.game.tetris.entity.UserEntity;
import com.game.tetris.service.UserService;
import com.game.tetris.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zjb
 * @date 2018/4/17.
 */
@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    @RequestMapping("/game1")
    public String indexGame1(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        boolean isLogin = false;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    UserEntity userEntity = userService.findByToken(token);
                    if (userEntity != null && token.equals(userEntity.getToken()) && System.currentTimeMillis() -
                            userEntity.getGmtUpdate().getTime() < 24 * 3600 * 1000) {
                        isLogin = true;
                    }
                }
            }
        }
        if(isLogin){
            return "game1";
        }else {
            return "login";
        }
    }

    @RequestMapping("/game2")
    public String indexGame2(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        boolean isLogin = false;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    UserEntity userEntity = userService.findByToken(token);
                    if (userEntity != null && token.equals(userEntity.getToken()) && System.currentTimeMillis() -
                            userEntity.getGmtUpdate().getTime() < 24 * 3600 * 1000) {
                        isLogin = true;
                    }
                }
            }
        }
        if(isLogin){
            return "game2";
        }else {
            return "login";
        }
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/addregister")
    public String register(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        if (password.equals(password2)){
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            userEntity.setToken(TokenUtils.createToken());
            userService.save(userEntity);
            Cookie cookie = new Cookie("token",userEntity.getToken());
            response.addCookie(cookie);
            return "game";
        }else {
            return "register";
        }
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/addlogin")
    public String login(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean isLogin = userService.isUserLogin(username,password);
        String str = "";
        if (isLogin == true){
            UserEntity userEntity = new UserEntity();
            userEntity.setToken(TokenUtils.createToken());
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            userService.update(userEntity);
            Cookie cookie = new Cookie("token",userEntity.getToken());
            response.addCookie(cookie);
            str = "game";
        }else {
            str = "login";
        }
        return str;
    }
}

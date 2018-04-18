package com.game.tetris.controller;

import com.game.tetris.entity.UserEntity;
import com.game.tetris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zjb
 * @date 2018/4/17.
 */
@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    @RequestMapping("/game")
    public String index() {
        System.out.println("in");
        return "game";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/addregister")
    public String register(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        if (password.equals(password2)){
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            userService.save(userEntity);
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
    public String login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean isLogin = userService.isUserLogin(username,password);
        String str = "";
        if (isLogin == true){
            str = "game";
        }else {
            str = "login";
        }
        return str;
    }
}

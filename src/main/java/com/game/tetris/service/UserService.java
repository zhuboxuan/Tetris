package com.game.tetris.service;

import com.game.tetris.entity.UserEntity;

/**
 * @author zjb
 * @date 2018/4/18.
 */
public interface UserService {

    boolean isUserLogin(String username, String password);

    boolean isTokenRight(String token);

    boolean save(UserEntity userEntity);
}

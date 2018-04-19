package com.game.tetris.util;

import java.util.UUID;

/**
 * @author zjb
 * @date 2018/4/18.
 */
public class TokenUtils {

    public static String createToken(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}

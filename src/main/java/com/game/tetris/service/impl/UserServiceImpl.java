package com.game.tetris.service.impl;

import com.game.tetris.entity.UserEntity;
import com.game.tetris.repository.UserRepository;
import com.game.tetris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author zjb
 * @date 2018/4/18.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isUserLogin(String username, String password) {
        UserEntity userEntity = userRepository.findByUsernameAndPassword(username,password);
        return userEntity != null;
    }

    @Override
    public boolean isTokenRight(String token) {
        return false;
    }

    @Override
    public boolean save(UserEntity userEntity) {
        UserEntity user = userRepository.save(userEntity);
        return user!=null;
    }

    @Override
    public boolean update(UserEntity userEntity) {
        userRepository.updateTokenByUser(userEntity.getUsername(), userEntity.getToken());
        return true;
    }

    @Override
    public UserEntity findByToken(String token) {

        return userRepository.findByToken(token);
    }

    @Override
    public UserEntity getMaxScore() {
        Sort sort = new Sort(Sort.Direction.DESC, "maxScore");
        List<UserEntity> list = userRepository.findAll(sort);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return new UserEntity();
    }

    @Override
    public boolean updateScore(UserEntity userEntity) {
        userRepository.updateScoreByToken(userEntity.getMaxScore(), userEntity.getToken());
        return true;
    }

    @Override
    public boolean updateScore2(UserEntity userEntity) {
        userRepository.updateScore2ByToken(userEntity.getMaxScore(), userEntity.getToken());
        return true;
    }

}

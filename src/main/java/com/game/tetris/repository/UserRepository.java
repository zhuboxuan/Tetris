package com.game.tetris.repository;

import com.game.tetris.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zjb
 * @date 2018/4/18.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    UserEntity findByUsernameAndPassword(String username,String password);
}

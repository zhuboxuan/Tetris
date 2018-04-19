package com.game.tetris.repository;

import com.game.tetris.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zjb
 * @date 2018/4/18.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    UserEntity findByUsernameAndPassword(String username,String password);

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.token = :token where u.username = :username")
    int updateTokenByUser(@Param("username") String username, @Param("token") String token);

    UserEntity findByToken(String token);
}



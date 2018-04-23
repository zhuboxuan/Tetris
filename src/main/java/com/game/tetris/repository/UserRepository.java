package com.game.tetris.repository;

import com.game.tetris.entity.UserEntity;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.maxScore = :maxScore where u.token = :token")
    int updateScoreByToken(@Param("maxScore") Integer score, @Param("token") String token);


    @Transactional
    @Modifying
    @Query("update UserEntity u set u.maxScore2 = :maxScore2 where u.token = :token")
    int updateScore2ByToken(@Param("maxScore2") Integer score, @Param("token") String token);
}



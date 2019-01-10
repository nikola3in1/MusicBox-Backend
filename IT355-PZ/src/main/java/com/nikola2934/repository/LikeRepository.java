
package com.nikola2934.repository;

import com.nikola2934.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query(value="SELECT * FROM `likes` WHERE likes.user_id = :user_id AND likes.song_id= :song_id",nativeQuery = true)
    public Like ifExists(@Param("user_id") Long user_id, @Param("song_id") Long song_id);
}

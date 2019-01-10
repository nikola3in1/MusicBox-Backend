package com.nikola2934.repository;

import com.nikola2934.model.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SongRepository extends JpaRepository<Song, Long> {
    @Query(value = "SELECT user.username,song.song_id, song.genre_id, song.user_id, "
            + "song.name, song.length,song.price,song.date,song.nr_purchases,song.about,song.path,song.path_w\n"
            + "FROM `song` LEFT JOIN user ON song.user_id = user.user_id \n"
            + "WHERE song.date >= NOW() + INTERVAL -7 DAY\n"
            + "AND song.date < NOW() + INTERVAL 0 DAY\n"
            + "ORDER BY song.nr_purchases DESC LIMIT 5", nativeQuery = true)
    public List<Song> getTop5();

    @Query(value = "SELECT song.song_id, song.genre_id, song.user_id,song.name, song.length,"
            + "song.price,song.date,song.nr_purchases,song.about,song.path,song.path_w "
            + "FROM `song` LEFT JOIN user ON song.user_id = user.user_id "
            + "WHERE song.name = :songName AND user.username = :username", nativeQuery = true)
    public Song findByNameAndUsername(String username, String songName);
    
    @Query(value="SELECT song.song_id, song.genre_id, song.user_id,song.name, song.length,"
            + "song.price,song.date,song.nr_purchases,song.about,song.path,song.path_w, "
            + "user.`user_id`, user.`name`, user.`lastname`, user.`earnings`, user.`username`,"
            + "user.`email`, user.`password`, user.`paypal_email`, user.`picture`, user.`text`, user.`active`"
            + "FROM song LEFT JOIN user ON song.user_id = user.user_id "
            + "WHERE song.name LIKE %:query%",nativeQuery = true)
    public List<Song> search(@Param("query")String query);
}

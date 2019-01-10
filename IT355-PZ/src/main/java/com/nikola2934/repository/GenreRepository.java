package com.nikola2934.repository;

import com.nikola2934.model.Genre;
import com.nikola2934.model.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GenreRepository extends JpaRepository<Genre, Long> {
//    @Query(value = "SELECT song.song_id, song.user_id, song.name, song.length, song.price, song.date, song.nr_purchases, song.about, song.path, song.path_w FROM genre as g LEFT JOIN song ON g.genre_id = song.song_id WHERE g.name =:genre", nativeQuery = true)
//    List<Song> getSongsByGenre(@Param("genre") String genre);
    @Query(value = "SELECT * FROM genre WHERE genre.name = :genre", nativeQuery = true)
    Genre getGenreByName(@Param("genre")String genre);
}


package com.nikola2934.service;

import com.nikola2934.model.Genre;
import com.nikola2934.model.Song;

import java.util.List;

public interface GenreService {
    public List<Genre> getAllGenres();
    public List<Genre> getGenres(Integer bound);
    public List<Song> getSongsByGenre(Genre genre);
    public List<Song> getSongsByGenre(String genre);
    public Genre getGenreByName(String genre);
    public void deleteGenre(Genre genre);
    public Boolean addGenre(Genre genre);
}

package com.nikola2934.service;

import com.nikola2934.model.Genre;
import com.nikola2934.model.Song;
import com.nikola2934.repository.GenreRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public List<Song> getSongsByGenre(Genre genre) {
        Genre myGenre = genreRepository.findById(genre.getGenre_id()).get();
        return myGenre.getSongs();
    }

    @Override
    public List<Song> getSongsByGenre(String genre) {
        Genre temp = getGenreByName(genre);
        if(temp != null){
            return temp.getSongs();
        }
        return new ArrayList();
    }

    @Override
    public Genre getGenreByName(String genre) {
        return genreRepository.getGenreByName(genre);
    }

    @Override
    public List<Genre> getGenres(Integer bound) {
        List<Genre> genres =genreRepository.findAll();
        if(genres.size()>bound){
            return genres.subList(0, bound);
        }
        return genres;
    }

    @Override
    public void deleteGenre(Genre genre) {
        genreRepository.delete(genre);
    }

    @Override
    public Boolean addGenre(Genre genre) {
        Object newGenre = genreRepository.save(genre);
        return (newGenre != null);
    }

}

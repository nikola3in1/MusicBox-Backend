package com.nikola2934.service;

import com.nikola2934.model.Song;
import com.nikola2934.repository.SongRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository songRepository;

    @Override
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public boolean buySong(Song song) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Song> getTop5() {
        return songRepository.getTop5();
    }

    @Override
    public void deleteSong(Song song) {
        songRepository.delete(song);
    }

    @Override
    public Song findById(Long id) {
        Optional<Song> song = songRepository.findById(id);
        if (song.isPresent()) {
            return song.get();
        } else {
            return new Song();
        }
    }

    @Override
    public Song findByNameAndUsername(String songName, String username) {
        return songRepository.findByNameAndUsername(username, songName);
    }

    @Override
    public void uploadSong(Song song) {
        songRepository.save(song);
    }

    @Override
    public List<Song> search(String query) {
        return songRepository.search(query);
    }
}

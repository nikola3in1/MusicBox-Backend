package com.nikola2934.service;

import com.nikola2934.model.Song;
import java.util.List;

public interface SongService {

    public List<Song> getAllSongs();

    public boolean buySong(Song song);

    public List<Song> getTop5();

    public void deleteSong(Song song);

    public Song findById(Long id);

    public Song findByNameAndUsername(String songName, String username);

    public void uploadSong(Song song);

    public List<Song> search(String query);
}

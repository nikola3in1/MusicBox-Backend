package com.nikola2934.service;

import com.nikola2934.model.Song;
import com.nikola2934.model.User;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SongService songService;

    @Autowired
    private UserService userService;

    @Override
    public Map<String, Object> search(String query) {
        Map<String, Object> results = new HashMap();
        Set<Song> songs = new HashSet();
        Set<User> users = new HashSet();
        String[] words = query.split(" ");
        for (String w : words) {
            songs.addAll(songService.search(w));
            users.addAll(userService.search(w));
        }
        System.out.println("s: "+songs.size() + " u: " + users.size());
        results.put("creators", users);
        results.put("songs", songs);

        return results;
    }

}

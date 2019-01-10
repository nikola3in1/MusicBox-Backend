
package com.nikola2934.service;

import com.nikola2934.model.Song;
import com.nikola2934.model.User;
import java.util.List;
import java.util.Set;

public interface UserService {
    void save(User user);
    void update(User user);
    User findByUsername(String username);
    List<User> getAll();
    List<Song> getUserSongs(User user);
    List<Song> getUserSongs(String user);
    void deleteUser(User user);
    Set<User> search(String query);
}

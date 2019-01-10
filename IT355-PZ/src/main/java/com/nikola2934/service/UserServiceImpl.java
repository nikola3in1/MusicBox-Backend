package com.nikola2934.service;

import com.nikola2934.model.Song;
import com.nikola2934.model.User;
import com.nikola2934.repository.RoleRepository;
import com.nikola2934.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Override
    public void save(User user) {
        //ZAMENI ROLE
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<Song> getUserSongs(User user) {
        return userRepository.findByUsername(user.getUsername()).getSongs();
    }

    @Override
    public List<Song> getUserSongs(String user) {
        User temp = userRepository.findByUsername(user);
        if(temp != null){
            return temp.getSongs();
        }
        return new ArrayList();
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public Set<User> search(String query) {
        return userRepository.search(query);
    }
    
}

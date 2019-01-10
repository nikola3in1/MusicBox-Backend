package com.nikola2934.service;

import com.nikola2934.model.Role;
import com.nikola2934.model.User;
import com.nikola2934.repository.RoleRepository;
import com.nikola2934.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    
    
    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }
    

}

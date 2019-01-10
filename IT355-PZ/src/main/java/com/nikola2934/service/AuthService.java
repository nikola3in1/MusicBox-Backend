
package com.nikola2934.service;

import com.nikola2934.model.Role;
import com.nikola2934.model.User;
import java.util.List;

public interface AuthService {
    User findByUsername(String username);
    List<Role> getRoles();
}

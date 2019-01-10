package com.nikola2934.config;

import com.nikola2934.model.Role;
import com.nikola2934.model.User;
import com.nikola2934.service.AuthService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private AuthService authService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String username = a.getName();
        String password = a.getCredentials().toString();
        User s = authService.findByUsername(username);
       
        if(s==null){
            throw new BadCredentialsException("BAD CREDENTIALS");
        }
        
        System.out.println("AUTH");
        if (bCryptPasswordEncoder.matches(password, s.getPassword())) {
            Set<Role> roles = authService.findByUsername(username).getRole();
            List<GrantedAuthority> grantedAuthorities = new ArrayList();
            for (Role r : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(r.getRole()));
            }
                        
            System.out.println("auth granted");
            Authentication auth = new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
            return auth;
        }
        throw new BadCredentialsException("BAD CREDENTIALS");

    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }

}

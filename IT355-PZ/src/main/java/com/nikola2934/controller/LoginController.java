package com.nikola2934.controller;

import com.nikola2934.model.Genre;
import com.nikola2934.model.Like;
import com.nikola2934.model.Report;
import com.nikola2934.model.Song;
import com.nikola2934.model.User;
import com.nikola2934.service.GenreService;
import com.nikola2934.service.LikeService;
import com.nikola2934.service.ReportService;
import com.nikola2934.service.SongService;
import com.nikola2934.service.UserService;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private SongService songService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/login")
    public Map<String, String> login(HttpServletRequest req) {
        Map<String, String> response = new HashMap();
        if(req.getUserPrincipal()!=null && !req.getUserPrincipal().getName().isBlank()){
            String username = req.getUserPrincipal().getName();
            response.put("success", "true");
            response.put("message", "all good");
            response.put("userame",username);
            response.put("type", "1");
            System.out.println("successful login");
            return response;
        }
        System.out.println("login failed");

        response.put("success", "false");
        response.put("message", "all good");
        response.put("type", "0");
        return response;
       
    }
    @PostMapping("/register")
    public Map<String,Object> register(@RequestBody Map<String,String> body){
        Map<String,Object> response = new HashMap();
        response.put("success",false);
        if(validate(body)){
            String username = body.get("username");
            String password = body.get("password");
            String email = body.get("email");
            String firstName = body.get("firstname");
            String lastName = body.get("lastname");
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setName(firstName);
            user.setLastname(lastName);
            userService.save(user);
            response.put("success",true);
        }
        return response;
    }
    
    private boolean validate(Map<String,String> body){
        return !body.get("username").isBlank() && !body.get("password").isBlank()
                && !body.get("email").isBlank() && !body.get("firstname").isBlank()
                && !body.get("lastname").isBlank();
    }
    
    
    @GetMapping("loginfail")
    public Map<String,String> loginFail(){
        System.out.println("LOGIN FAIL");
        Map<String,String> response = new HashMap();
        response.put("success", "false");
        response.put("message", "all good");
        response.put("type", "0");
        return response;
    }
    
    
    @GetMapping("/logout")
    public void logout(HttpServletRequest req) {
        String username = req.getUserPrincipal().getName();
        System.out.println(username + " logged out");
    }

    @GetMapping("/user")
    public Map<String, Object> auth(HttpServletRequest req) {
        Collection<SimpleGrantedAuthority> authorities
                = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Map<String, Object> response = new HashMap();

        String username = "";

        if (req.getUserPrincipal() != null) {
            //not authorized
            username =req.getUserPrincipal().getName();
            System.out.println("AUTHOVAN");
        }else{
            System.out.println("NIJE AUTHOVAN");

        }

        response.put("message", "");
        response.put("success", false);
        response.put("type", 1);
        response.put("initLogin", false);
        response.put("username", "");

        if (!authorities.isEmpty() && !authorities.contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            String role = authorities.toArray()[0].toString();
//            System.out.println("role: "+Arrays.toString(authorities.toArray()));
//            System.out.println("username: "+username);
            User currUser = userService.findByUsername(username);
            response.put("message", role.toLowerCase());
            response.put("success", true);
            response.put("username", username);
            response.put("type", role.toLowerCase());
//            System.out.println(currUser+ " curruser");
            if (currUser.getPaypal_email().isBlank()) {
                response.put("initLogin", true);
            }
        }
        return response;
    }

    @GetMapping("/test")
    public String test() {
        Genre genre = new Genre("Punk", "punked");
        genreService.addGenre(genre);
        return "added " + genre.getName();
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        List<User> users = userService.getAll();
        return users;
    }

    @GetMapping("/likes")
    public List<Like> getLikes() {
        return likeService.getAllLikes();
    }

    @GetMapping("/songs")
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @PostMapping("/save")
    public User register(@RequestBody User req) {
        userService.save(req);
        return req;
    }
}

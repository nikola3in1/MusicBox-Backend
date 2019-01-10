package com.nikola2934.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nikola2934.model.Genre;
import com.nikola2934.model.Like;
import com.nikola2934.model.Report;
import com.nikola2934.model.Song;
import com.nikola2934.model.User;
import com.nikola2934.service.GenreService;
import com.nikola2934.service.LikeService;
import com.nikola2934.service.ReportService;
import com.nikola2934.service.SongService;
import com.nikola2934.service.StorageService;
import com.nikola2934.service.UserService;
import java.security.Principal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = {"http://localhost:4200/**"}, maxAge = 3000)
@RestController
@RequestMapping("/rest/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private SongService songService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private StorageService storageService;
    
    @GetMapping("/profile")
    public User getCreatorProfile(HttpServletRequest req) {
        String username = req.getUserPrincipal().getName();
        System.out.println(username);
        User temp = userService.findByUsername(username);
        return temp;
    }

    @PostMapping("/like")
    public Map<String, String> likeSong(@RequestBody Map<String, String> body, HttpServletRequest req) {
        String username = req.getUserPrincipal().getName();
        System.out.println("likeer: "+username);
        User liker = userService.findByUsername(username);
        String creator = body.get("creator");
        String songName = body.get("songName");
        Song song = songService.findByNameAndUsername(songName, creator);
        Like like = new Like(liker, song);
        Boolean val = likeService.likeSong(like);
        Map<String, String> response = new HashMap();
        response.put("status", val + "");
        return response;
    }

    @PostMapping("/report")
    public Map<String, Object> addGenre(@RequestBody Map<String, Object> body, HttpServletRequest req) {
        Map<String, Object> response = new HashMap();
        response.put("success",false);
        
        String reason = (String) body.get("reason");
        String songName = (String) body.get("songname");
        String creatorUsername = (String) body.get("creator");
        String reporterUsername = req.getUserPrincipal().getName();
        Song song = songService.findByNameAndUsername(songName, creatorUsername);
        User reporter = userService.findByUsername(reporterUsername);
        Report newReport = new Report(reason, song, reporter);

        Boolean value = reportService.addReport(newReport);
        if (value) {
            response.put("message", "too many reports");
            response.put("success", true);
        }
        return response;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadSong(@RequestParam("song") MultipartFile songFile,
            @RequestParam("wsong") MultipartFile song_wFile,
            @RequestParam("name") String songName,
            @RequestParam("genre") String genreName,
            @RequestParam("about") String about,
            HttpServletRequest req) {
        Map<String, Object> body = new HashMap();

        String username = req.getUserPrincipal().getName();
        Song song = songService.findByNameAndUsername(songName, username);
        if (song == null) {
            Genre genre = genreService.getGenreByName(genreName);
            if (genre == null) {
                body.put("message", "error");
                body.put("success", false);
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
            }
            User user = userService.findByUsername(username);
            song = new Song();
            song.setUser(user);
            song.setName(songName);
            song.setGenre(genre);
            song.setAbout(about);
            song.setDate(new Date(System.currentTimeMillis()));
            song.setNr_purchases(0);
            String songPath = storageService.saveSong(songFile, song);
            String song_wPath = storageService.saveSongWatermarked(song_wFile, song);
            song.setLength(storageService.getSongDuration(songPath));
            song.setPath(songPath);
            song.setPath_w(song_wPath);
            songService.uploadSong(song);
            body.put("message", "You successfully uploaded !");
            body.put("success", true);
            return ResponseEntity.status(HttpStatus.OK).body(body);
        }
        body.put("message", "error");
        body.put("success", false);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<Map<String, Object>> updateProfile(@RequestParam("picture") MultipartFile picture,
            @RequestParam("about") String about, @RequestParam("paypal") String paypalEmail,
            HttpServletRequest req) {
        Map<String, Object> body = new HashMap();
        String newAbout = about;
        String newPayPalEmail = paypalEmail;
        if (req.getUserPrincipal() == null || req.getUserPrincipal().getName() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
        }
        String username = req.getUserPrincipal().getName();

        System.out.println(username + " user principal ");
        if (!newAbout.isEmpty() && !newPayPalEmail.isEmpty()) {
            String picturePath = storageService.savePicture(picture, username);
            if (!picturePath.equals("error")) {
                User user = userService.findByUsername(username);
                user.setPicture(picturePath);
                user.setText(about);
                user.setPaypal_email(paypalEmail);
                userService.update(user);

                body.put("success", true);
                return ResponseEntity.status(HttpStatus.OK).body(body);

            }
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
    }

    @PostMapping("/deleteSong")
    public Map<String, Object> deleteSong(@RequestBody Map<String, String> body,
            HttpServletRequest req) {
        Map<String, Object> response = new HashMap();
        if (req.getUserPrincipal() == null || req.getUserPrincipal().getName() == null) {
            response.put("success", false);
            return response;
        }
        String username = req.getUserPrincipal().getName();
        String songName = body.get("songname");
        Song song = songService.findByNameAndUsername(songName, username);
        songService.deleteSong(song);
        storageService.deleteSong(song);
        response.put("success", true);
        return response;
    }

    List<Song> checkForLikes(List<Song> songs, Principal userPrincipal) {
        System.out.println("CHECKING FOR LIKES");
        if (userPrincipal != null) {
            System.out.println("checking likes");
            Principal principal = userPrincipal;
            String username = principal.getName();
            for (Song song : songs) {
                for (Like l : song.getLikes()) {
                    if (l.getLiker().getUsername().equals(username)) {
                        song.setLikedByMe(true);
                        System.out.println("FOUND LIKE");
                        break;
                    }
                }
            }
        }
        return songs;
    }
    
//    public Map<String, Object> auth(HttpServletRequest req) {
//        Map<String, Object> response = new HashMap();
//        if (req.getUserPrincipal() != null && !req.getUserPrincipal().getName().isBlank()) {
//            String username = req.getUserPrincipal().getName();
//            response.put("message","fail");
//            response.put("success", false);
//            response.put("type", "0");
//            response.put("initLogin", false);
//            response.put("username", username);
//
//        }
//    }
}

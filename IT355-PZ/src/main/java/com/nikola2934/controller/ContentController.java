package com.nikola2934.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nikola2934.model.Genre;
import com.nikola2934.model.Like;
import com.nikola2934.model.Purchase;
import com.nikola2934.model.Song;
import com.nikola2934.model.User;
import com.nikola2934.serializer.GenreSerializer;
import com.nikola2934.service.GenreService;
import com.nikola2934.service.PurchaseService;
import com.nikola2934.service.SearchService;
import com.nikola2934.service.SongService;
import com.nikola2934.service.StorageService;
import com.nikola2934.service.UserService;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/rest/content/")
public class ContentController {

    @Autowired
    private SongService songService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private UserService userService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private SearchService searchService;

    @PostMapping("/profile")
    public User getProfile(@RequestBody Map<String, String> body, HttpServletRequest req) throws JsonProcessingException {
        if (body.containsKey("creator")) {
            User temp = userService.findByUsername(body.get("creator"));
            List<Song> songs = temp.getSongs();
            songs = checkForLikes(songs, req.getUserPrincipal());
            temp.setSongs(songs);
            return temp;
        }
        return new User();
    }

    @GetMapping("/getTop5")
    public Map<String, Object> getTop5(HttpServletRequest req) {
        Map<String, Object> response = new HashMap();
        List<Song> songs = songService.getTop5();
        songs = checkForLikes(songs,req.getUserPrincipal());
        response.put("songs", songs);
        return response;
    }

    @GetMapping("/songs")
    public Map<String, Object> getSongsByGenreOrCreator(@RequestParam("genre") Optional<String> genre,
            @RequestParam("creator") Optional<String> creator, HttpServletRequest req) {
        List<Song> songs = new ArrayList();

        if (genre.isPresent()) {
            songs = genreService.getSongsByGenre(genre.get());
        } else if (creator.isPresent()) {
            songs = userService.getUserSongs(creator.get());
        }

        Map<String, Object> response = new HashMap();
        //Checking if liked by user
        /*DEV MODE*/
        songs = checkForLikes(songs, req.getUserPrincipal());
        response.put("songs", songs);

//        if (req.getUserPrincipal() != null) {
//            System.out.println("checking likes");
//            Principal principal = req.getUserPrincipal();
//            String username = principal.getName();
//            for (Song s : songs) {
//                for (Like l : s.getLikes()) {
//                    if (l.getLiker().getUsername().equals(username)) {
//                        s.setLikedByMe(true);
//                        break;
//                    }
//                }
//            }
//        }
        return response;
    }

    @PostMapping("/genres")
    public String getGeneres(@RequestBody Map<String, Integer> body) throws JsonProcessingException {
        List<Genre> genres = new ArrayList();
        Integer index = body.get("limit");
        String response = "[]";

        System.out.println("duda");

        if (index != null && index > 0) {
            genres = genreService.getGenres(index);
        } else {
            genres = genreService.getAllGenres();
        }

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Genre.class, new GenreSerializer());
        mapper.registerModule(module);
        response = mapper.writeValueAsString(genres);
        return response;
    }

    @GetMapping("/link")
    public String testLink() {
        Song song = songService.getAllSongs().get(0);
        String link = purchaseService.generateDownloadKey(song);
        return link;
    }

    @GetMapping("/resource")
    public ResponseEntity<Resource> download(HttpServletResponse response,
            HttpServletRequest request, @RequestParam("p") String path) {
        if (!path.isBlank()) {
            System.out.println("serving song!");
            //Sending a file
            File file = new File(path);
            Resource resource = storageService.loadFileAsResource(file);

            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
            }

            // Fallback to the default content type if type could not be determined
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    //                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        }
        return null;
    }

    @PostMapping("/buy")
    public Map<String, Object> buySong(@RequestBody Map<String, String> body, HttpServletRequest req
    ) {
        Map<String, Object> response = new HashMap();
        response.put("success", false);
        if (invalid(body)) {
            return response;
        }
        String buyerIp = req.getRemoteAddr();
        String buyerEmail = body.get("paypalEmail");
        String creator = body.get("creator");
        String songName = body.get("songName");
        Song song = songService.findByNameAndUsername(songName, creator);
        if (song != null) {
            Long tomorrowMs = System.currentTimeMillis();
            tomorrowMs += 24 * 60 * 60 * 1000;
            Date tomorrowDate = new Date(tomorrowMs);
            //Generating key
            String key = purchaseService.generateDownloadKey(song);
            //Making purchase
            Purchase newPurchase = new Purchase();
            newPurchase.setPaypal_email(buyerEmail);
            newPurchase.setBuyer_ip(buyerIp);
            newPurchase.setSong(song);
            newPurchase.setLink_exp_date(tomorrowDate);
            newPurchase.setSecret(key);
            //FIX LINK
            newPurchase.setDownload_link(key);
            //FIX LINK
            purchaseService.makePurchase(newPurchase);
            response.put("k", key);
            response.put("success", true);
        }
        return response;
    }

    @GetMapping("/song")
    public Song getSong(@RequestParam("creator") String creator,
            @RequestParam("songName") String songName, HttpServletRequest req) {
        Song song = songService.findByNameAndUsername(songName, creator);
        List<Song> songs = new ArrayList();
        songs.add(song);
        song = checkForLikes(songs, req.getUserPrincipal()).get(0);
        return song;
    }

    @PostMapping("/search")
    public Map<String, Object> search(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap();
        response.put("creators", new String[0]);
        response.put("songs", new String[0]);
        if (body.get("query") != null && !body.get("query").isBlank()) {
            String query = body.get("query");
            response = searchService.search(query);
            return response;

        }
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
        }else{
            System.out.println("No user principal");
        }
        return songs;
    }

    public boolean invalid(Map<String, String> body) {
        return body.get("paypalEmail") == null
                || body.get("paypalEmail").isBlank() || body.get("creator").isBlank() || body.get("songName").isBlank();
    }
}

package com.nikola2934.controller;

import com.nikola2934.model.Genre;
import com.nikola2934.model.Report;
import com.nikola2934.model.Song;
import com.nikola2934.model.User;
import com.nikola2934.service.GenreService;
import com.nikola2934.service.ReportService;
import com.nikola2934.service.SongService;
import com.nikola2934.service.UserService;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/admin")
public class AdminController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private GenreService genreService;
    
    @Autowired
    private SongService songService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/reports")
    public Map<String,Object> getReports() {
        Map<String,Object> response = new HashMap();
        List<Report> reports= reportService.getAll();
        response.put("reports",reports);
        return response;
    }
    
    @PostMapping("/deleteSong")
    public Map<String,Object> deleteSong(@RequestBody Map<String,String> body){
        Map<String, Object> response = new HashMap();
        response.put("success", false);
        if(body.get("creator")!=null && !body.get("creator").isBlank()
                && body.get("songName")!=null && !body.get("songName").isBlank()){
            String songName = body.get("songName");
            String creator = body.get("creator");
            Song song = songService.findByNameAndUsername(songName, creator);
            if(song!=null){
                songService.deleteSong(song);
                response.put("success",true);
            }
        }
        return response;
    }
    
    @PostMapping("/deleteReport")
    public Map<String,Object> deleteReport(@RequestBody Map<String,Number> body){
        Map<String,Object> response = new HashMap();
        response.put("success",false);
        if(body.get("reportid")!=null && body.get("reportid").intValue() >= 0){
            Long reportId = body.get("reportid").longValue();
            Report report = reportService.findById(reportId);
            if(report!=null){
                reportService.deleteReport(report);
                response.put("success",true);
            }
        }
        return response;
    }

    @PostMapping("/addGenre")
    public Map<String, Object> addGenre(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap();
        response.put("success",false);
        if (validateAddGenre(body)) {
            String name = body.get("name");
            String about= body.get("about");
            Genre newGenre = new Genre();
            newGenre.setName(name);
            newGenre.setAbout(about);
            Boolean value = genreService.addGenre(newGenre);
            if(value){
                response.put("success", true);
            }
        }
        return response;
    }

    @PostMapping("/deleteGenre")
    public Map<String,Object> deleteGenre(@RequestBody Map<String,String> body,
            HttpServletRequest req){
        Map<String,Object> response = new HashMap();
        response.put("success",false);
        if(body.get("name")!=null && !body.get("name").isBlank()){
            String name = body.get("name");
            System.out.println(name+ " genre name");
            Genre genreToDelete = genreService.getGenreByName(name);
            genreService.deleteGenre(genreToDelete);
            response.put("success",true);
        }
        return response;
    }
    
    @PostMapping("/deleteCreator")
    public Map<String,Object> deleteCreator(@RequestBody Map<String,String> body,
            HttpServletRequest req){
        Map<String, Object> response = new HashMap();
        response.put("success", false);
        if (body.get("creator") != null && !body.get("creator").isBlank()) {
            String username = body.get("creator");
            System.out.println(username + " creator name");
            User user = userService.findByUsername(username);
            userService.deleteUser(user);
            response.put("success", true);
        }
        return response;
    }
    
    
    private boolean validateAddGenre(Map<String, String> body) {
        return body.get("name") != null && !body.get("name").isBlank()
                && body.get("about") !=null && !body.get("about").isBlank();
    }
}

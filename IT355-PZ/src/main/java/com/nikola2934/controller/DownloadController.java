package com.nikola2934.controller;

import com.nikola2934.model.Genre;
import com.nikola2934.model.Purchase;
import com.nikola2934.model.Song;
import com.nikola2934.model.User;
import com.nikola2934.service.GenreService;
import com.nikola2934.service.PurchaseService;
import com.nikola2934.service.SongService;
import com.nikola2934.service.StorageService;
import com.nikola2934.service.UserService;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private SongService songService;

    @Autowired
    private UserService userService;

    @GetMapping("download")
    public ResponseEntity<Resource> download(@RequestParam("k") String buyerKey,
            HttpServletResponse response, HttpServletRequest request) {
        if (!buyerKey.isBlank()) {
            Purchase purchase = purchaseService.findPurchaseByKey(buyerKey);
            System.out.println("looking for purchase...");

            if (purchase != null && validatePurchase(purchase)) {
                System.out.println("serving song!");
                purchase.setIs_downloaded(1);
                String songPath = purchase.getSong().getPath();
                purchaseService.finished(purchase);

                //Sending a file
                File song = new File(songPath);
                Resource resource = storageService.loadFileAsResource(song);

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
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            }
        }
        return null;
    }

    private boolean validatePurchase(Purchase purchase) {
        return purchase.getLink_exp_date().after(new Date(System.currentTimeMillis()))
                && purchase.getIs_downloaded() == 0;
    }
    
}

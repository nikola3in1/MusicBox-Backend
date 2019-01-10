package com.nikola2934.service;

import com.nikola2934.model.Song;
import java.io.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    public String savePicture(MultipartFile picture,String username);
    public String saveSong(MultipartFile songFile, Song song);
    public String saveSongWatermarked(MultipartFile songWFile, Song song);
    public Integer getSongDuration(String fileName);
    public Resource loadFileAsResource(File file);
    public void deleteSong(Song song);
}

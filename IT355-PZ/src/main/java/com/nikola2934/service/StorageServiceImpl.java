package com.nikola2934.service;

import com.nikola2934.model.Song;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation = Paths.get("uploads");
    private final Path picturesDir = Paths.get("uploads/pictures");
    private final Path songsDir = Paths.get("uploads/songs");
    private final Path songsWDir = Paths.get("uploads/songsW");

    public boolean saveFile(MultipartFile file, Path path) {
        try {
            File check = new File(path.toAbsolutePath().toString());
            if (check.exists()) {
                check.delete();
            }
            Files.copy(file.getInputStream(), path);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    @Override
    public String savePicture(MultipartFile picture, String username) {
        String orgFilename = picture.getOriginalFilename();
        String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);
        Path path = picturesDir.resolve(username + "." + extension);
        Boolean status = saveFile(picture, path);
        if (status) {
            return path.toString();
        }
        return "error";
    }

    @Override
    public String saveSong(MultipartFile songFile, Song song) {
        String orgFilename = songFile.getOriginalFilename();
        String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);
        String fileNameRaw = song.getUser().getUsername() + "-" + song.getName();
        Path path = songsDir.resolve(fileNameRaw + "." + extension);
        Boolean status = saveFile(songFile, path);
        if (status) {
            return path.toString();
        }
        return "error";
    }

    @Override
    public Integer getSongDuration(String fileName) {
        Integer durationSeconds = 0;
        File file = new File(fileName);
        try {
            AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(file);
            Map properties = baseFileFormat.properties();
            Long sec = (Long) properties.get("duration") / 1000000;
            durationSeconds = Math.toIntExact(sec);
        } catch (IOException | UnsupportedAudioFileException e) {
        }
        return durationSeconds;
    }

    @Override
    public String saveSongWatermarked(MultipartFile songW, Song song) {
        String orgFilename = songW.getOriginalFilename();
        String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);
        String fileName = song.getUser().getUsername() + "-" + song.getName();
        Path path = songsWDir.resolve(fileName + "." + extension);
        Boolean status = saveFile(songW, path);
        if (status) {
            return path.toString();
        }
        return "error";
    }

    @Override
    public Resource loadFileAsResource(File file) {
        try {
            Path filePath = file.toPath();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new Exception("file not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteSong(Song song) {
        try {
            if (!deleteFile(song.getPath()) || !deleteFile(song.getPath_w())) {
                throw new Exception("file not found");
            }
        } catch (Exception ex) {
            Logger.getLogger(StorageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean deleteFile(String path) {
        File file = new File(path);
        if (file.delete()) {
            return true;
        }
        return false;
    }
}

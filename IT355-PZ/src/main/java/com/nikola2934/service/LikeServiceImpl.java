package com.nikola2934.service;

import com.nikola2934.model.Like;
import com.nikola2934.model.Song;
import com.nikola2934.repository.LikeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepository likeRepository;
    
    
    @Override
    public List<Like> getAllLikes() {
        return likeRepository.findAll();
    }

    @Override
    public Boolean likeSong(Like songLike) {
        /*check if exists,
            if yes -> dislike
            else -> like
        */
        Like liked = likeRepository.ifExists(songLike.getLiker().getUser_id(), songLike.getLikes().getSong_id());
        Boolean status=false;
        if(liked != null){
            likeRepository.delete(liked);
        }else{
            likeRepository.save(songLike);
            status=true;
        }
        return status;
    }

}

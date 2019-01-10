
package com.nikola2934.service;

import com.nikola2934.model.Like;
import java.util.List;

public interface LikeService {
    public List<Like> getAllLikes();
    public Boolean likeSong(Like songLike);
}

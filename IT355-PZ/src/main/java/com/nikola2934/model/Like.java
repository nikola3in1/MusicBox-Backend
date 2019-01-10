package com.nikola2934.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "likes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "likes_id")
public class Like implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likes_id;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User liker;
    
    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song likes;

    public Like(){}
    
    public Like(User liker, Song likes) {
        this.liker = liker;
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Like{" + "likes_id=" + likes_id + ", liker=" + liker + ", likes=" + likes + '}';
    }

    public Integer getLikes_id() {
        return likes_id;
    }

    public void setLikes_id(Integer likes_id) {
        this.likes_id = likes_id;
    }

    public User getLiker() {
        return liker;
    }

    public void setLiker(User liker) {
        this.liker = liker;
    }

    public Song getLikes() {
        return likes;
    }

    public void setLikes(Song likes) {
        this.likes = likes;
    }
    
    
}

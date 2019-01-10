package com.nikola2934.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nikola2934.serializer.SongSerializer;
import com.nikola2934.serializer.SongShortSerializer;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Song")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "song_id")
@JsonSerialize(using = SongSerializer.class)
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long song_id;

    private String name;
    private Integer length;
    private Double price;
    private Date date;
    private Integer nr_purchases;
    private String about;
    private String path;
    private String path_w;
    @Transient
    private Boolean likedByMe=false;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "likes")
    private Set<Like> likes;
    
    @OneToMany(mappedBy = "reportedSong")
    @JsonIgnore
    private List<Report> reports;
    
    @OneToMany(mappedBy = "song")
    @JsonIgnore
    private List<Purchase> purchases;
    

    @Override
    public String toString() {
        return "Song{" + "song_id=" + song_id + ", genre=" + genre + ", user=" + user + ", name=" + name + ", length=" + length + ", price=" + price + ", date=" + date + ", nr_purchases=" + nr_purchases + ", about=" + about + ", path=" + path + ", path_w=" + path_w + '}';
    }

    public Boolean getLikedByMe() {
        return likedByMe;
    }

    public void setLikedByMe(Boolean likedByMe) {
        this.likedByMe = likedByMe;
    }
    
    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Long getSong_id() {
        return song_id;
    }

    public void setSong_id(Long song_id) {
        this.song_id = song_id;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
        if(length>120){
            this.price=5.0;
        }else{
            this.price=3.0;
        }
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNr_purchases() {
        return nr_purchases;
    }

    public void setNr_purchases(Integer nr_purchases) {
        this.nr_purchases = nr_purchases;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath_w() {
        return path_w;
    }

    public void setPath_w(String path_w) {
        this.path_w = path_w;
    }

}

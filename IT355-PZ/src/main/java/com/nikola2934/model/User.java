package com.nikola2934.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nikola2934.serializer.UserSerializer;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "User")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "user_id")
@JsonSerialize(using = UserSerializer.class)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @Column
    private String name;
    @Column
    private String lastname;
    @Column
    private Double earnings = 0.0;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String paypal_email = "";
    @Column
    private String picture = "";
    @Column
    private String text = "";
    @Column
    private Integer active = 0;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE})
    @JoinTable(name = "roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonBackReference
    private Set<Role> roles;

    @OneToMany(mappedBy = "liker")
    @JsonIgnore
    private List<Like> likes;

    @OneToMany(mappedBy = "reporter")
    private List<Report> submitedReports;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Song> songs;

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", name=" + name + ", lastname=" + lastname + ", earnings=" + earnings + ", username=" + username + ", email=" + email + ", password=" + password + ", paypal_email=" + paypal_email + ", picture=" + picture + ", text=" + text + ", active=" + active + ", roles=" + roles + ", likes=" + likes + ", submitedReports=" + submitedReports + ", songs=" + songs + '}';
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Double getEarnings() {
        return earnings;
    }

    public void setEarnings(Double earnings) {
        this.earnings = earnings;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPaypal_email() {
        return paypal_email;
    }

    public void setPaypal_email(String paypal_email) {
        this.paypal_email = paypal_email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Role> getRole() {
        return roles;
    }

    public void setRole(Set<Role> role) {
        this.roles = role;
    }

}

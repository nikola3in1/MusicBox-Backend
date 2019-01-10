package com.nikola2934.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "purchase")
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchase_id;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "song_id")
    private Song song;
    private String download_link;
    private String paypal_email;
    private String buyer_ip;
    private Date link_exp_date;
    private Integer is_downloaded = 0;
    private String secret;

    public Purchase() {
    }

    public String getBuyer_ip() {
        return buyer_ip;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setBuyer_ip(String buyer_ip) {
        this.buyer_ip = buyer_ip;
    }

    public String getPaypal_email() {
        return paypal_email;
    }

    public void setPaypal_email(String buyer_email) {
        this.paypal_email = buyer_email;
    }

    public Integer getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(Integer purchase_id) {
        this.purchase_id = purchase_id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getDownload_link() {
        return download_link;
    }

    public void setDownload_link(String download_link) {
        this.download_link = download_link;
    }

    public Date getLink_exp_date() {
        return link_exp_date;
    }

    public void setLink_exp_date(Date link_exp_date) {
        this.link_exp_date = link_exp_date;
    }

    public Integer getIs_downloaded() {
        return is_downloaded;
    }

    public void setIs_downloaded(Integer is_downloaded) {
        this.is_downloaded = is_downloaded;
    }

}

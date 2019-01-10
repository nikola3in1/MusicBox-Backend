package com.nikola2934.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nikola2934.serializer.ReportSerializer;
import java.io.Serializable;
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
@Table(name = "report")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "report_id")
@JsonSerialize(using = ReportSerializer.class)
public class Report implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long report_id;
    private String reason;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "song_id")
    private Song reportedSong;

    @ManyToOne
    @JoinColumn(name = "reported_by")
    private User reporter;

    public Report(){}
    
    public Report(String reason, Song reportedSong, User reporter) {
        this.reason = reason;
        this.reportedSong = reportedSong;
        this.reporter = reporter;
    }
    
    public Long getReport_id() {
        return report_id;
    }

    public void setReport_id(Long report_id) {
        this.report_id = report_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Song getReportedSong() {
        return reportedSong;
    }

    public void setReportedSong(Song reportedSong) {
        this.reportedSong = reportedSong;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

}

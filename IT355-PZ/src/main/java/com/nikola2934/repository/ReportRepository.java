package com.nikola2934.repository;

import com.nikola2934.model.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report,Long>{
    @Query(value = "SELECT * FROM `report` WHERE report.song_id = :song_id AND report.reported_by = :reported_by", nativeQuery = true)
    public List<Report> findReportsByUserAndSong(@Param("reported_by") Long reported_by, @Param("song_id") Long song_id);
}

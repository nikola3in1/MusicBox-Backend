package com.nikola2934.service;

import com.nikola2934.model.Report;
import com.nikola2934.repository.ReportRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    private ReportRepository reportRepository;
    
    @Override
    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    @Override
    public void deleteReport(Report report) {
        reportRepository.delete(report);
    }

    @Override
    public Boolean addReport(Report report) {
        Integer nrOfReports = reportRepository.findReportsByUserAndSong(report.getReporter().getUser_id()
                , report.getReportedSong().getSong_id()).size();
        if(nrOfReports < 3){
            reportRepository.save(report);
            return true;
        }
        return false;
    }

    @Override
    public Report findById(Long id) {
       Optional<Report> report = reportRepository.findById(id);
       if(report.isPresent()){
           return report.get();
       }
       return null;
    }
}

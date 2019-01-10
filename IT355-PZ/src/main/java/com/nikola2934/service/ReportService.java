package com.nikola2934.service;

import com.nikola2934.model.Report;
import java.util.List;

public interface ReportService {
    public List<Report> getAll();
    public void deleteReport(Report report);
    public Boolean addReport(Report report);
    public Report findById(Long id);
}

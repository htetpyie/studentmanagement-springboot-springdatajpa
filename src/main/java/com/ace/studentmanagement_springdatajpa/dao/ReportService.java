package com.ace.studentmanagement_springdatajpa.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.ace.studentmanagement_springdatajpa.entity.Student;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class ReportService {
    
    @Autowired
    private StudentRepository repository;

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException{
        String path = "C:\\Users\\htetpyie\\Desktop\\";
        List<Student> students = repository.findAll();        
        File file = ResourceUtils.getFile("classpath:jasper/StudentReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBY", "Htet Pyie");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if(reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path+"student.html");
        }

        if(reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint, path+"student.pdf");
        }

        if(reportFormat.equalsIgnoreCase("excel")){
            JRXlsxExporter exporter = new JRXlsxExporter(); // initialize exporter 
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); // set compiled report as input
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path+"students.xlsx"));  // set output file via path with filename
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true); // setup configuration
            configuration.setDetectCellType(true);
            exporter.setConfiguration(configuration); // set configuration
            exporter.exportReport();
             }
        return  "redirect:/";
    
    }
}

package com.test.maybank.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itextpdf.text.DocumentException;
import com.test.maybank.demo.adaptor.GithubCallService;
import com.test.maybank.demo.adaptor.dto.response.GithubUserResponse;
import com.test.maybank.demo.entity.HistoryEntity;
import com.test.maybank.demo.repository.HistoryRepository;
import com.test.maybank.demo.util.file.PdfGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

@Service
public class MainService {
    private GithubCallService githubCallService;
    private PdfGenerator<GithubUserResponse.Item> pdfGenerator;
    private HistoryRepository historyRepository;

    public MainService(GithubCallService githubCallService, PdfGenerator<GithubUserResponse.Item> pdfGenerator, HistoryRepository historyRepository) {
        this.githubCallService = githubCallService;
        this.pdfGenerator = pdfGenerator;
        this.historyRepository = historyRepository;
    }

    public byte[] getFileService(Integer perPage,String fileName) throws JsonProcessingException, DocumentException {
        var githubCallResponse=githubCallService.getUser(perPage);

        var pdfFile=pdfGenerator.generatePdf(githubCallResponse.getItems(),List.of("login","id","nodeId","avatarUrl","gravatarId","url","type","siteAdmin","score"));

        var format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        var formatDateTime = LocalDateTime.now().format(format);

        var newFileName = fileName.replace(".pdf", "") + "_" + formatDateTime + ".pdf";

        historyRepository.save(HistoryEntity.builder()
                        .fileName(newFileName)
                        .downloadedDateTime(LocalDateTime.now())
                        .lastDownloadedFile(Base64.getEncoder().encodeToString(pdfFile))
                        .build());

        return pdfFile;
    }

    public List<HistoryEntity> getListPdf() {
        return historyRepository.findAll();
    }

    public byte[] downloadPdf(long idHistory) {
        var dataHistory = historyRepository.findById(idHistory);

        byte[] pdfFile = Base64.getDecoder().decode(dataHistory.getLastDownloadedFile());

        return pdfFile;
    }
}

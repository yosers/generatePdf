package com.test.maybank.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itextpdf.text.DocumentException;
import com.test.maybank.demo.adaptor.GithubCallService;
import com.test.maybank.demo.adaptor.dto.response.GithubUserResponse;
import com.test.maybank.demo.entity.HistoryEntity;
import com.test.maybank.demo.repository.HistoryRepository;
import com.test.maybank.demo.util.file.PdfGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.mockito.Mockito.*;

public class MainServiceTest {

    private MainService mainService;

    @Mock
    private GithubCallService githubCallService;

    @Mock
    private PdfGenerator pdfGenerator;

    @Mock
    private HistoryRepository historyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mainService = new MainService(githubCallService, pdfGenerator, historyRepository);
    }
    @Test
    public void testGetFileService() throws JsonProcessingException, DocumentException {
        when(githubCallService.getUser(any())).thenReturn(GithubUserResponse.builder()
                        .incompleteResults(false)
                        .totalCount(8777)
                        .items(List.of(GithubUserResponse.Item.builder()
                                        .type("A")
                                        .id("1")
                                        .avatarUrl("http://1234.com")
                                        .eventsUrl("http://2345.com")
                                        .followersUrl("http://3456.com")
                                        .followingUrl("http://4567.com")
                                        .gistsUrl("http://5678.com")
                                        .gravatarId("grav123")
                                        .htmlUrl("https://github.com")
                                        .login("yes")
                                        .nodeId("node123")
                                        .organizationsUrl("org123")
                                        .receivedEventsUrl("received123")
                                        .url("123")
                                        .reposUrl("123")
                                        .score(new BigDecimal(123))
                                        .siteAdmin(false)
                                        .starredUrl("starred")
                                        .subscriptionsUrl("subs1")
                                        .type("admin")
                                .build()))
                .build());
        // Mock response from GithubCallService
        int perPage = 10;
        // ... create a mock GithubCallResponse

        // Mock response from PdfGenerator
        byte[] mockPdfFile = "Sample PDF content".getBytes();
        when(pdfGenerator.generatePdf(any(),any())).thenReturn(mockPdfFile);

        // Perform the service method call
        mainService.getFileService(perPage, "sample.pdf");

        // Verify that the methods were called with the expected parameters
        verify(githubCallService, times(1)).getUser(perPage);
        verify(pdfGenerator, times(1)).generatePdf(any(),any());
        verify(historyRepository, times(1)).save(any(HistoryEntity.class));
    }

    @Test
    public void getListPdf() {
        var listHistory = new ArrayList<HistoryEntity>();

        var temp = new HistoryEntity();
        temp.setId(1L);
        temp.setFileName("simple.pdf");
        temp.setDownloadedDateTime(LocalDateTime.now());
        temp.setLastDownloadedFile("test");

        listHistory.add(temp);

        when(historyRepository.findAll()).thenReturn(listHistory);

        mainService.getListPdf();
    }

    @Test
    public void downloadPdf() {

        var parameter = 1L;
        var temp = new HistoryEntity();
        temp.setId(1L);
        temp.setFileName("simple.pdf");
        temp.setDownloadedDateTime(LocalDateTime.now());
        temp.setLastDownloadedFile("test");

        when(historyRepository.findById(parameter)).thenReturn(temp);

        byte[] pdfFile = Base64.getDecoder().decode(temp.getLastDownloadedFile());

        mainService.downloadPdf(parameter);
    }
}

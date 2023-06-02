package com.test.maybank.demo.controller;

import com.test.maybank.demo.entity.HistoryEntity;
import com.test.maybank.demo.service.MainService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MainControllerTest {

    private MainController mainController;

    @Mock
    private MainService mainService;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mainController = new MainController(mainService);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testGetFile() throws Exception {
        // Mock parameters
        int perPage = 10;

        // Mock service response
        byte[] fileContent = "Sample file content".getBytes();
        when(mainService.getFileService(perPage, "sample.pdf")).thenReturn(fileContent);

        // Perform the GET request
        ResponseEntity<byte[]> response = mainController.getFile(perPage);

        // Verify the service method was called with the correct parameters
        verify(mainService, times(1)).getFileService(perPage, "sample.pdf");

        // Verify the response status code
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the response headers
        HttpHeaders headers = response.getHeaders();
        assertEquals("application/pdf", headers.getContentType().toString());
        assertEquals("attachment; filename=sample.pdf", headers.getFirst(HttpHeaders.CONTENT_DISPOSITION));

        // Verify the response body
        assertEquals(fileContent, response.getBody());
    }

    @Test
    void testGelistPdf() {
        var listHistory = new ArrayList<HistoryEntity>();

        var temp = new HistoryEntity();
        temp.setId(1L);
        temp.setFileName("simple.pdf");
        temp.setDownloadedDateTime(LocalDateTime.now());
        temp.setLastDownloadedFile("test");
        listHistory.add(temp);

        when(mainService.getListPdf()).thenReturn(listHistory);

        var response = mainController.getListPdf();

        // Verify the response body
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getDownloadPdf() {
        var temp = new HistoryEntity();
        temp.setId(1L);
        temp.setFileName("simple.pdf");
        temp.setDownloadedDateTime(LocalDateTime.now());
        temp.setLastDownloadedFile("test");

        byte[] fileContent = "Sample file content".getBytes();

        when(mainService.downloadPdf(1)).thenReturn(fileContent);

        var response = mainController.getDownloadPdf(1L);

        // Verify the response body
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}

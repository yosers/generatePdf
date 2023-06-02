package com.test.maybank.demo.controller;

import com.test.maybank.demo.dto.Constant;
import com.test.maybank.demo.entity.HistoryEntity;
import com.test.maybank.demo.service.MainService;
import com.test.maybank.demo.util.file.Util;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
@Validated
public class MainController extends BaseController {

    public static final String SAMPLE_PDF = "sample.pdf";
    public static final String SAMPLE_ZIP = "sample.zip";
    private final MainService mainService;

    @GetMapping("/user/file")
    public ResponseEntity<byte[]> getFile(@RequestParam @Min(value = 1,message = "input minimum is 1") @Max(value = 100,message = "input maximum is below 100") Integer perPage) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, Util.getTargetTypeByFileExtension(SAMPLE_PDF));
        headers.add(HttpHeaders.CONTENT_DISPOSITION, Constant.ATTACHMENT_FILENAME + SAMPLE_PDF);

        return new ResponseEntity<>(mainService.getFileService(perPage,SAMPLE_PDF),
                headers,
                HttpStatus.OK);
    }

    @GetMapping("/history/list-pdf")
    public ResponseEntity<List<HistoryEntity>>getListPdf(){

        return new ResponseEntity<>( mainService.getListPdf(), HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> getDownloadPdf(@RequestParam long idHistory) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, Util.getTargetTypeByFileExtension(SAMPLE_PDF));
        headers.add(HttpHeaders.CONTENT_DISPOSITION, Constant.ATTACHMENT_FILENAME + SAMPLE_PDF);

        return new ResponseEntity<>(mainService.downloadPdf(idHistory),
                headers,
                HttpStatus.OK);
    }
}

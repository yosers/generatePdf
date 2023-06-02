package com.test.maybank.demo.controller;

import com.test.maybank.demo.dto.CommonRs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    private static final String SUCCESS="success";

    public <T> ResponseEntity<CommonRs<T>> ok(T data) {
        return new ResponseEntity<>(new CommonRs<>(HttpStatus.OK.value(), SUCCESS, null, data), HttpStatus.OK);
    }

}

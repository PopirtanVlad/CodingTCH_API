package com.example.licenta.controller;


import com.example.licenta.dtos.user.security.ApiResponse;
import com.example.licenta.entities.TestCase;
import com.example.licenta.services.TestCasesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/testcases")
public class TestCasesController {

    @Autowired
    private TestCasesServiceImpl testCasesService;

    @PostMapping("/add")
    public ResponseEntity<?> uploadTestCases(@RequestParam(value = "files", required = false) MultipartFile[] files){
        testCasesService.saveTestCases(files);
        return ResponseEntity.ok().body(new ApiResponse(true, "Test cases added successfully"));
    }
}

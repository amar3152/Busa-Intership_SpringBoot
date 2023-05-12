package com.busa.controller;

import com.busa.service.AmazonClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage/")
public class HomeController {

    @Autowired
    private AmazonClientService amazonClientService;


    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam(value = "file")MultipartFile file){
        return amazonClientService.uploadFile(file);
    }

}

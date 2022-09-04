package com.example.wil.controller;

import com.example.wil.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class ImageController {
    private final ImageService imageService;

    /*@PostMapping("/images")
    public String upload(@RequestParam("image")MultipartFile multipartFile)throws IOException {
        awsS3Service.upload(multipartFile, "static");
        return "image upload success!!";
    }*/

    @PostMapping("/images")
    public String upload(@RequestParam("image") List<MultipartFile> multipartFile)throws IOException {
        if (multipartFile == null) {
            System.out.println("multipartFile = null");
        }
        List<String> imgPath = imageService.upload(multipartFile, "static");

//        awsS3Service.upload(multipartFile, "static");
        return "image upload success!!";
    }
//    @GetMapping("/images")
//    public List<ImagePost> getImages(@RequestParam("image")MultipartFile multipartFile)throws IOException {
//
//        return imageService.findAll();
//    }



}
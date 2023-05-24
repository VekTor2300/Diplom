package com.example.individualpr.Controllers;

import com.example.individualpr.Models.ImageDocument;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/imageDocument")
public class ImageController {

    @GetMapping("{imageDocument}")
    private ResponseEntity<?> imageIndex(@PathVariable ImageDocument imageDocument){
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(imageDocument.getContentType()))
                .contentLength(imageDocument.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(imageDocument.getBytes())));
    }
}
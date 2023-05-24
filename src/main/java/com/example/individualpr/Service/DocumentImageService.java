package com.example.individualpr.Service;

import com.example.individualpr.Models.Documents;
import com.example.individualpr.Models.ImageDocument;
import com.example.individualpr.Repos.DocumentsRepos;
import com.example.individualpr.Repos.ImageDocRepos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentImageService {
    private final DocumentsRepos documentsRepos;

    private final ImageDocRepos imageRepository;

    public void saveImageAndDocuments(Documents documents, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            ImageDocument imageDocument = saveImageEntity(file);
            imageRepository.save(imageDocument);
            documents.setImageDocument(imageDocument);
        }
        documentsRepos.save(documents);
    }

    private ImageDocument saveImageEntity(MultipartFile file) throws IOException {
        ImageDocument imageDocument = new ImageDocument();
        imageDocument.setName(file.getName());
        imageDocument.setOriginalFileName(file.getOriginalFilename());
        imageDocument.setContentType(file.getContentType());
        imageDocument.setSize(file.getSize());
        imageDocument.setBytes(file.getBytes());
        return imageDocument;
    }
}

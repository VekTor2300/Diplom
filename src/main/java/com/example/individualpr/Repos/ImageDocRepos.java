package com.example.individualpr.Repos;

import com.example.individualpr.Models.Documents;
import com.example.individualpr.Models.ImageDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDocRepos extends JpaRepository<ImageDocument, Long> {
    ImageDocument findImageDocumentByDocuments(Documents documents);
}

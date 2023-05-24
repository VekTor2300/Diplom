package com.example.individualpr.Repos;

import com.example.individualpr.Models.Documents;
import com.example.individualpr.Models.User;
import com.lowagie.text.List;
import org.springframework.data.repository.CrudRepository;

public interface DocumentsRepos extends CrudRepository<Documents,Long> {
    boolean existsByUser(User user);
}
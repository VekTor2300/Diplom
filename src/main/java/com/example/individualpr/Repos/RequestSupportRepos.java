package com.example.individualpr.Repos;

import com.example.individualpr.Models.RequestSupport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RequestSupportRepos extends JpaRepository<RequestSupport,Long> {
    List<RequestSupport> findByDateApp(Date dateApp);
}

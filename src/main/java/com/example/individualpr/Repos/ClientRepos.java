package com.example.individualpr.Repos;

import com.example.individualpr.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepos extends JpaRepository<Client,Long> {
}
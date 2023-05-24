package com.example.individualpr.Repos;

import com.example.individualpr.Models.AppStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppStatusRepos extends CrudRepository<AppStatus,Long> {
    List<AppStatus> findByName(String name);
}

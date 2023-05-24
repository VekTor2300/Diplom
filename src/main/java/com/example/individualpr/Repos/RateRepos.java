package com.example.individualpr.Repos;

import com.example.individualpr.Models.Rate;
import com.example.individualpr.Models.RequestSupport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RateRepos extends JpaRepository<Rate,Long> {
    List<Rate> findByNameRate (String nameRate);
}

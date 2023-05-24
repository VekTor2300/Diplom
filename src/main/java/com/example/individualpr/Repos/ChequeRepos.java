package com.example.individualpr.Repos;

import com.example.individualpr.Models.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChequeRepos extends JpaRepository<Cheque,Long> {
    List<Cheque> findByQuantity(Long quantity);

}
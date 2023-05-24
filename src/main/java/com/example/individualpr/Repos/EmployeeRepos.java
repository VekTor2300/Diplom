package com.example.individualpr.Repos;

import com.example.individualpr.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepos extends JpaRepository<Employee,Long> {

    List<Employee> findByNumberphoneContains(Long numberphone);

    List<Employee> findByNumberphone(Long numberphone);

}
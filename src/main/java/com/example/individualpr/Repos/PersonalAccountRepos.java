package com.example.individualpr.Repos;

import com.example.individualpr.Models.PersonalAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalAccountRepos extends JpaRepository<PersonalAccount,Long> {

}
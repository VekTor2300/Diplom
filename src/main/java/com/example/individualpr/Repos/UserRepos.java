package com.example.individualpr.Repos;

import com.example.individualpr.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepos extends CrudRepository<User,Long> {
    User findByLogin(String username);


    List<User> findByActive(boolean active);

    default List<User> findActive() {
        return this.findByActive(true);
    }
}

package com.example.individualpr.Repos;

import com.example.individualpr.Models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepos extends CrudRepository<Post,Long> {
    Post findByTitlepost(String titlepost);

}
package com.example.individualpr.Repos;

import com.example.individualpr.Models.ChatMessage;
import com.example.individualpr.Models.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepos extends JpaRepository<ChatMessage,Long> {
    List<ChatMessage> findByContent (String Content);

}
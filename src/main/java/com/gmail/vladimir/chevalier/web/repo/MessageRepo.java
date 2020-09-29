package com.gmail.vladimir.chevalier.web.repo;

import com.gmail.vladimir.chevalier.web.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}

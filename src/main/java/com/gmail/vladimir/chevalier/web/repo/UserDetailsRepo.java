package com.gmail.vladimir.chevalier.web.repo;

import com.gmail.vladimir.chevalier.web.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User, String> {
}

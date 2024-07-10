package com.example.backend.repository;

import com.example.backend.entity.RecoverAccount;
import com.example.backend.entity.RecoverAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RecoverAccountRepo extends JpaRepository<RecoverAccount, Integer> {
     Optional<RecoverAccount> findByResetCodeAndUserId(String resetCode, Integer userId);
     Optional<RecoverAccount> findByUserEmail(String email);


}


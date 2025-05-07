package com.tms.repository;

import com.tms.model.entity.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {
    Boolean existsByLogin(String login);
    Optional<Security> findByLogin(String login);
}

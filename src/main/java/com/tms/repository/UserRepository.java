package com.tms.repository;

import com.tms.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmailOrTelephoneNumber(String email, String telephoneNumber);
}

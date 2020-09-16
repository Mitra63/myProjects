package com.sadad.ib.repository;

import com.sadad.ib.entity.RestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<RestUser, Long> {
    Optional<RestUser> findByUsername(String username);
    List<RestUser> findAll();
}

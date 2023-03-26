package com.example.stockapp2.repositories;

import com.example.stockapp2.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository <Authority,Long> {

    Optional<Authority> findByName(String authority);
}

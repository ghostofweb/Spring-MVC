package com.runnerapp.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.runnerapp.web.models.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long>{
    Optional<Club>findByTitle(String url );
}

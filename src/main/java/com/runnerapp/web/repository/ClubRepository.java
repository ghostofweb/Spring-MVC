package com.runnerapp.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.runnerapp.web.models.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long>{
    Optional<Club>findByTitle(String url );
    @Query("SELECT c FROM Club c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Club>searchClubs(String query);
}

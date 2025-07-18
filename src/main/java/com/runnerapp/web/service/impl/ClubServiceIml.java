package com.runnerapp.web.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.runnerapp.web.dto.ClubDto;
import com.runnerapp.web.models.Club;
import com.runnerapp.web.repository.ClubRepository;
import com.runnerapp.web.service.ClubService;

@Service
public class ClubServiceIml implements ClubService {
    private ClubRepository clubRepository;

    public ClubServiceIml(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public List<ClubDto> findAllClubs() {
         List<Club> clubs =clubRepository.findAll();

         return clubs.stream()
         .map((club) -> mapToClubDto(club))
         .collect(Collectors.toList());
        
    }
    private ClubDto mapToClubDto(Club club){
        ClubDto clubDto = ClubDto.builder()
        .id(club.getId())
        .title(club.getTitle())
        .photoUrl(club.getPhotoUrl())
        .content(club.getContent())
        .createdOn(club.getCreatedOn())
        .updatedOn(club.getUpdatedOn())
        .build();
    return clubDto;
    }
}

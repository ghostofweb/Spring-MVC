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

    @Override
    public Club saveClub(Club club) {
        return clubRepository.save(club);
    }

    @Override
    public ClubDto findClubById(long clubId) {
        Club club = clubRepository.findById(clubId).get();
        return mapToClubDto(club);
    }

    @Override
    public void updateClub(ClubDto clubDto) {
        Club club = mapToClub(clubDto);
        clubRepository.save(club);
    }

    @Override
    public void deleteClub(Long clubId) {
        clubRepository.deleteById(clubId);
    }

    @Override
    public List<ClubDto> searchClubs(String query) {
        List<Club> clubs = clubRepository.searchClubs(query);
        return clubs.stream().map(club -> mapToClubDto(club)).collect(Collectors.toList());
    }

    private Club mapToClub(ClubDto club) {
        Club clubDto = Club.builder()
                .id(club.getId())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .build();
        return clubDto;
    }
}

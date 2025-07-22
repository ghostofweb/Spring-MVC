package com.runnerapp.web.service;

import java.util.List;

import com.runnerapp.web.dto.ClubDto;
import com.runnerapp.web.models.Club;

public interface ClubService {
List<ClubDto> findAllClubs();
Club saveClub(Club club);
ClubDto findClubById(long clubId);
void updateClub(ClubDto club);
}

package com.runnerapp.web.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.runnerapp.web.dto.ClubDto;
import com.runnerapp.web.models.Club;
import com.runnerapp.web.service.ClubService;

@Controller
public class ClubController {
    private final ClubService clubService;
    
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/clubs")
    public String listClubs(Model model) {
        List<ClubDto> clubs = clubService.findAllClubs();
        model.addAttribute("clubs",clubs);
        return "clubs-list";
    }

    @GetMapping("/clubs/new")
    public String createClubForm(Model model) {
        Club club = new Club();
        model.addAttribute("club",club);
        return "clubs-create";
    }
    
    @PostMapping("/clubs/new")
    public String saveClub(@Valid @ModelAttribute("club") Club club,BindingResult result){
        if(result.hasErrors()){
            return "clubs-edit";
        }
        clubService.saveClub(club);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/edit")
    public String editClub(@PathVariable("clubId")long clubID,Model model){
        ClubDto club = clubService.findClubById(clubID);
        model.addAttribute("club",club);
        return "clubs-edit";
    }

    @PostMapping("/clubs/{clubId}")
    public String updateClub(@PathVariable("clubId") Long clubId,
                             @ModelAttribute("club") ClubDto club){
        club.setId(clubId);
        clubService.updateClub(club);
        return "redirect:/clubs";
    }

    @PostMapping("/clubs/{clubId}/delete")
    public String deleteClub(@PathVariable("clubId") Long clubId){
        clubService.deleteClub(clubId);
        return "redirect:/club";
    }


}

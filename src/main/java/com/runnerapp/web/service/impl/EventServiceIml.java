package com.runnerapp.web.service.impl;

import com.runnerapp.web.dto.EventDto;
import com.runnerapp.web.models.Club;
import com.runnerapp.web.models.Event;
import com.runnerapp.web.repository.ClubRepository;
import com.runnerapp.web.repository.EventRepository;
import com.runnerapp.web.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceIml implements EventService {
    private final EventRepository eventRepository;
    private final ClubRepository clubRepository;

    @Autowired
    public EventServiceIml(EventRepository eventRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public void createEvent(Long clubId, EventDto eventDto) {
    Club club = clubRepository.findById(clubId).get();
    Event event = mapToEvent(eventDto);
    event.setClub(club);
    eventRepository.save(event);
    }

    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events= eventRepository.findAll();
        return events.stream().map(event -> mapToEventDto(event)).collect(Collectors.toList());

    }

    public static Event mapToEvent(EventDto eventDto) {
        return Event.builder()
                .id(eventDto.getId())
                .name(eventDto.getName())
                .type(eventDto.getType())
                .startTime(eventDto.getStartTime())
                .endTime(eventDto.getEndTime())
                .photoUrl(eventDto.getPhotoUrl())
                .createdOn(eventDto.getCreatedOn())
                .updatedOn(eventDto.getUpdatedOn())
                .build();

    }

    public static EventDto mapToEventDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .type(event.getType())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .photoUrl(event.getPhotoUrl())
                .createdOn(event.getCreatedOn())
                .updatedOn(event.getUpdatedOn())
                .build();

    }
}

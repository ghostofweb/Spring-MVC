package com.runnerapp.web.service;

import com.runnerapp.web.dto.EventDto;

public interface EventService {
    void createEvent(Long clubId, EventDto eventDto);
}

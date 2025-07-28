package com.runnerapp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTIme;
    private String type;
    private String photoUrl;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}

    package com.runnerapp.web.dto;

    import java.time.LocalDateTime;

    import jakarta.validation.constraints.NotEmpty;
    import lombok.Builder;
    import lombok.Data;

    @Data
    @Builder
    public class ClubDto {
        private Long id;
        @NotEmpty(message = "Club Title is needed")
        private String title;
        @NotEmpty(message = "Photo Link is required")
        private String photoUrl;
        @NotEmpty(message = "Post content is required")
        private String content;
        private LocalDateTime createdOn;
        private LocalDateTime updatedOn;
    }

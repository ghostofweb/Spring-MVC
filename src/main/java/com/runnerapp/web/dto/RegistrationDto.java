package com.runnerapp.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    private Long id;
    @NotEmpty(message = "Put some username bro")
    private String username;
    @NotEmpty(message = "Put some email, it doesn't cost anything")
    private String email;
    @NotEmpty(message = "Put some password which u will forget just after 1 minute, you with gold fish memory")
    private String password;
}

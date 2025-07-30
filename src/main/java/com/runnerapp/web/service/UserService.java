package com.runnerapp.web.service;

import com.runnerapp.web.dto.RegistrationDto;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
}

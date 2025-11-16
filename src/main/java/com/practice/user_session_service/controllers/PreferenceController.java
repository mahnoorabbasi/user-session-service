package com.practice.user_session_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.user_session_service.commands.PreferenceCommandService;
import com.practice.user_session_service.dtos.ResponseObject;
import com.practice.user_session_service.entities.UserPreference;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PreferenceController {

    private final PreferenceCommandService preferenceCommandService;
//    private final PreferenceQueryService preferenceQueryService;



    @PostMapping("/v1/user-preference")
    public ResponseEntity updatePreference(@RequestBody  UserPreference userPreference) throws JsonProcessingException {
        ResponseObject responseObject=preferenceCommandService.updatePreference(userPreference);

        return new ResponseEntity<>(responseObject.getMsg(), responseObject.getStatus());

    }
}

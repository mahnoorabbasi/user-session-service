package com.practice.user_session_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.user_session_service.commands.PreferenceCommandService;
import com.practice.user_session_service.dtos.ResponseObject;
import com.practice.user_session_service.entities.UserPreference;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user-preference")

@AllArgsConstructor
public class PreferenceController {

    private final PreferenceCommandService preferenceCommandService;
//    private final PreferenceQueryService preferenceQueryService;



    @PostMapping("/update")
    public ResponseEntity updatePreference(@RequestBody  UserPreference userPreference) throws JsonProcessingException {
        ResponseObject responseObject=preferenceCommandService.updatePreference(userPreference);

        return new ResponseEntity<>(responseObject.getMsg(), responseObject.getStatus());

    }
    @PostMapping("/create")
    public ResponseEntity createPreference(@RequestBody  UserPreference userPreference) throws JsonProcessingException {
        ResponseObject responseObject=preferenceCommandService.createPreference(userPreference);

        return new ResponseEntity<>(responseObject.getMsg(), responseObject.getStatus());

    }


    @GetMapping
    public ResponseEntity healthCheck(){
        return new ResponseEntity<>("Hearbeat", HttpStatus.OK);
    }
}

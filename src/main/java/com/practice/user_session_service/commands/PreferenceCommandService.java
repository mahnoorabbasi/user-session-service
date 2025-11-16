package com.practice.user_session_service.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.user_session_service.dtos.EventObject;
import com.practice.user_session_service.dtos.ResponseObject;
import com.practice.user_session_service.entities.UserPreference;
import com.practice.user_session_service.utils.AggregateType;
import com.practice.user_session_service.utils.KafkaTopics;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PreferenceCommandService implements KafkaTopics {

    private final KafkaTemplate kafkaTemplate;
    private final ObjectMapper objectMapper;


    public ResponseObject updatePreference(UserPreference userPreference) throws JsonProcessingException {
        EventObject eventObject=EventObject.createCommand(userPreference.getUser_id(),
                AggregateType.UserPreference.toString(),
                CommandType.UPDATE_PREFERENCE.toString(),
                objectMapper.writeValueAsString(userPreference));
        kafkaTemplate.send(prefCommandTopic, objectMapper.writeValueAsString(eventObject));
        return ResponseObject.builder()
                .status(HttpStatus.OK)
                .msg("Command sent")
                .build();

    }
    public ResponseObject createPreference(UserPreference userPreference) throws JsonProcessingException {
        EventObject eventObject=EventObject.createCommand(userPreference.getUser_id(),
                AggregateType.UserPreference.toString(),
                CommandType.CREATE_PREFERENCE.toString(),
                objectMapper.writeValueAsString(userPreference));
        kafkaTemplate.send(prefCommandTopic, objectMapper.writeValueAsString(eventObject));
        return ResponseObject.builder()
                .status(HttpStatus.OK)
                .msg("Command sent")
                .build();

    }


    public enum CommandType{
        UPDATE_PREFERENCE,
        CREATE_PREFERENCE
    }


    //kafka publisher to preference_command topic
    //two types of events : create preference evet, update preference event








}

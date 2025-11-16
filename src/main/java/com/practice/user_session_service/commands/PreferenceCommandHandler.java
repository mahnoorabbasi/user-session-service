package com.practice.user_session_service.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.user_session_service.dtos.EventObject;
import com.practice.user_session_service.entities.EventStore;
import com.practice.user_session_service.entities.UserPreference;
import com.practice.user_session_service.repositories.EventStoreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import static com.practice.user_session_service.utils.KafkaTopics.prefCommandTopic;
import static com.practice.user_session_service.utils.KafkaTopics.prefEventTopic;

@Component
@Slf4j
@AllArgsConstructor
public class PreferenceCommandHandler {


    //kafka consumer for preference_command topic - updateABC
    //validates the input, and schema and all
    //then submit it to the pref_event topic - ABCUpdated
    //a consumer will listen to it and then make update in redis, and then into postgresql

    private final ObjectMapper objectMapper;
    KafkaTemplate kafkaTemplate;
    EventStoreRepository eventStoreRepository;

    @KafkaListener(
            topics= prefCommandTopic,
            groupId ="pref-command-group",
            containerFactory ="kafkaListenerContainerFactory"

    )
    public void onMessage(String message, Acknowledgment ack){
        try{
            EventObject eventObject=objectMapper.readValue(message, EventObject.class);
            // Confirm it's a command
            if (!eventObject.getMessageType().equals(EventObject.MessageType.COMMAND)) {
                log.warn("Ignoring non-command message type: {}", eventObject.getMessageType());
                return;
            }
            // Extract the actual payload (preferences)
            UserPreference pref = objectMapper.convertValue(
                    eventObject.getPayload(),
                    UserPreference.class
            );
            log.info("Applying command {} for user {}", eventObject.getAction(), pref.getUser_id());
            ack.acknowledge();
            convertCmdToEventAndSaveToEventStoreAndSendToEventTopic(eventObject, pref);


        }catch (Exception e){
            log.error("Error while deserializing the msg: "+e.getMessage());
        }
    }

    private void convertCmdToEventAndSaveToEventStoreAndSendToEventTopic(EventObject eventObject, UserPreference pref) throws JsonProcessingException {

        EventObject event=convertCommandToEvent(eventObject);
        eventStoreRepository.save(EventStore.builder()
                        .aggregate_id(event.getAggregateId())
                        .aggregate_type(event.getAggregateType())
                        .event_type(event.getMessageType().name())
                        .payload(objectMapper.writeValueAsString(pref))
                .build());
        kafkaTemplate.send(prefEventTopic,objectMapper.writeValueAsString(event));






    }

    private EventObject convertCommandToEvent(EventObject eventObject) {
        eventObject.setAction(eventObject.getAction() .equals(PreferenceCommandService.CommandType.CREATE_PREFERENCE)?"preference created":"preference updated");
        eventObject.setMessageType(EventObject.MessageType.EVENT);
        return eventObject;


    }


}

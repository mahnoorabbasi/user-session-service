package com.practice.user_session_service.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class ResponseObject {

    Object object;
    HttpStatus status;
    String msg;

}

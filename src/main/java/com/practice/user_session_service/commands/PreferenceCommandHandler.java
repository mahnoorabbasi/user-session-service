package com.practice.user_session_service.commands;

public class PreferenceCommandHandler {


    //kafka consumer for preference_command topic - updateABC
    //validates the input, and schema and all
    //then submit it to the pref_event topic - ABCUpdated
    //a consumer will listen to it and then make update in redis, and then into postgresql



}

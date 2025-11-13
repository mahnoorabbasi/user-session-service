package com.practice.user_session_service.repositories;

import com.practice.user_session_service.entities.App_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<App_User, UUID> {


}

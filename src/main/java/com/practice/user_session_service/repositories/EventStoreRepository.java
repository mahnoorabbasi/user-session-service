package com.practice.user_session_service.repositories;

import com.practice.user_session_service.entities.Event_Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventStoreRepository extends JpaRepository<Event_Store,String> {
}

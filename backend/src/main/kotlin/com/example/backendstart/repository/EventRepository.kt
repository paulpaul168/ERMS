package com.example.backendstart.repository

import com.example.backendstart.model.Event
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EventRepository : JpaRepository<Event, UUID> {
}
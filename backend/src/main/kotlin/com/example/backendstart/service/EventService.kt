package com.example.backendstart.service

import com.example.backendstart.model.Event
import com.example.backendstart.repository.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class EventService {
    @Autowired
    private val eventRepository: EventRepository? = null
    fun list(): List<Event> {
        return eventRepository?.findAll() ?: list()
    }

    fun save(entity: Event): Event? {
        return eventRepository?.save(entity)
    }

    fun getById(id: UUID): Event? {
        return eventRepository?.getReferenceById(id)
    }

}
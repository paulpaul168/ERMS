package com.example.backendstart.controller

import com.example.backendstart.model.Event
import com.example.backendstart.service.EventService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*


@RestController
class Default(private val eventService: EventService) {
    @GetMapping("/")
    fun index(): String {
        return "Event Service"
    }


    @PostMapping(value = ["/create-event"], consumes = ["application/json"], produces = ["application/json"])
    fun createEvent(@RequestBody createEvent: CreateEvent): Event? {
        val location = calcLocation(createEvent.beacons)
        val event = Event(
            deviceId = createEvent.deviceId,
            lat = location.lat,
            long = location.long,
            height = location.height,
            message = createEvent.message,
            date = Instant.now(),
            checked = false
        )
        return eventService.save(event)
    }

    data class UpdateEvent(val id: UUID, val checked: Boolean)

    @PostMapping(value = ["/update-event"], consumes = ["application/json"], produces = ["application/json"])
    fun updateEvent(@RequestBody updateEvent: UpdateEvent, response: HttpServletResponse): Event? {
        val event = eventService.getById(updateEvent.id)
        if (event == null) {
            throw NotFoundException()
        } else {
            event.checked = updateEvent.checked
            return eventService.save(event)
        }
    }

    @GetMapping("/events")
    fun allEvents(): Collection<Event> {
        return eventService.list()
    }

}

data class CreateBeacon(
    val id: UUID,
    val RSSI: Double
)

data class CreateEvent(
    val deviceId: String,
    val message: String,
    val beacons: List<CreateBeacon>
)

class Location(val lat: Double, val long: Double, val height: Double);

//TODO: Implement fancy calculation
fun calcLocation(beacons: Collection<CreateBeacon>): Location {
    return Location(0.0, 0.0, 0.0)
}



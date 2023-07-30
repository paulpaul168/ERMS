package com.example.backendstart.controller

import com.example.backendstart.calculation.LocationCalc
import com.example.backendstart.model.Event
import com.example.backendstart.service.BeaconService
import com.example.backendstart.service.EventService
import com.example.backendstart.service.PersonService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import kotlin.random.Random


@RestController
class Default(
    private val eventService: EventService,
    private val personService: PersonService,
    private val beaconService: BeaconService
) {
    @GetMapping("/")
    fun index(): String {
        return "Event Service"
    }


    @PostMapping(value = ["/events"], consumes = ["application/json"], produces = ["application/json"])
    fun createEvent(@RequestBody createEvent: CreateEvent): Event? {
        val locationCalc = LocationCalc()
        val beacons = beaconService.list()
        val location = locationCalc.calc(createEvent.beacons.toTypedArray(), beacons)
        val persons = personService.list();

        val event = Event(
            deviceId = createEvent.deviceId,
            x = location.x,
            y = location.y,
            z = location.z,
            message = createEvent.message,
            date = Instant.now(),
            checked = false,
            from = persons.getOrNull(Random.nextInt(persons.size))
        )
        return eventService.save(event)
    }

    data class UpdateEvent(val id: UUID, val checked: Boolean)

    @PatchMapping(value = ["/events"], consumes = ["application/json"], produces = ["application/json"])
    fun updateEvent(@RequestBody updateEvent: UpdateEvent, response: HttpServletResponse): Event? {
        val event = eventService.getById(updateEvent.id)
        if (event == null) {
            throw NotFoundException()
        } else {
            event.checked = updateEvent.checked
            return eventService.save(event)
        }
    }

    data class EventResponse(
        val id: UUID,
        val deviceId: String,
        val location: Int,
        val message: String,
        val date: Instant,
        var checked: Boolean,
        val from: String
    )

    @GetMapping("/events")
    fun allEvents(@RequestParam checked: String?): Collection<EventResponse> {

        val events = eventService.list()

        return events.filter {
            if (checked != null) {
                it.checked == (checked == "true")
            } else {
                true
            }
        }.sortedBy { it.date }.map {
            EventResponse(
                id = it.id,
                deviceId = it.deviceId,
                location = Random.nextInt(880),
                message = it.message,
                date = it.date,
                checked = it.checked,
                from = it.from?.name ?: ""
            )
        }
    }

}

data class CreateBeacon(
    val id: String,
    val RSSI: Double
)

data class CreateEvent(
    val deviceId: String,
    val message: String,
    val beacons: List<CreateBeacon>
)

class Location(val x: Double, val y: Double, val z: Double);




package com.example.backendstart.model

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(
    name = "event"
)
class Event(
    @Column(nullable = false)
    val deviceId: String,
    val lat: Double,
    val long: Double,
    val height: Double,
    val message: String,
    val date: Instant,
    var checked: Boolean
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID
}

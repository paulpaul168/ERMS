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
    val x: Double,
    val y: Double,
    val z: Double,
    val message: String,
    val date: Instant,
    var checked: Boolean,
    @OneToOne
    val from: Person?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID
}

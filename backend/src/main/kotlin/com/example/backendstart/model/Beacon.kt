package com.example.backendstart.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(
    name = "beacon"
)
class Beacon(
    lat: Float,
    long: Float,
    height: Float
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID
}
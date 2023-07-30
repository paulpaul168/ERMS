package com.example.backendstart.model

import jakarta.persistence.*

@Entity
@Table(
    name = "beacon"
)
class Beacon(
    val lat: Double,
    val long: Double,
    val x: Double,
    val y: Double,
    val z: Double
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}
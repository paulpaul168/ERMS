package com.example.backendstart.model

import jakarta.persistence.*

@Entity
@Table(
    name = "beacon"
)
class Beacon(
    x: Float,
    y: Float,
    z: Float
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}
package com.example.backendstart.model

import jakarta.persistence.*
import java.util.*


@Entity
@Table(
    name = "person"
)
class Person(
    val name: String,
    val deviceId: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID
}
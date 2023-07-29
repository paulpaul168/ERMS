package com.example.backendstart.repository

import com.example.backendstart.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PersonRepository : JpaRepository<Person, UUID> {

}
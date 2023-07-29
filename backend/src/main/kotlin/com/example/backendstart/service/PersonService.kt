package com.example.backendstart.service

import com.example.backendstart.model.Person
import com.example.backendstart.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PersonService {
    @Autowired
    private val personRepository: PersonRepository? = null
    fun list(): List<Person> {
        return personRepository?.findAll() ?: list()
    }

    fun save(entity: Person): Person? {
        return personRepository?.save(entity)
    }

    fun getById(id: UUID): Person? {
        return personRepository?.getReferenceById(id)
    }
}
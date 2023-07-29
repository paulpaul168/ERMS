package com.example.backendstart.service

import com.example.backendstart.model.Beacon
import com.example.backendstart.repository.BeaconRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class BeaconService {
    @Autowired
    private val beaconService: BeaconRepository? = null
    fun list(): List<Beacon> {
        return beaconService?.findAll() ?: list()
    }

    fun save(entity: Beacon): Beacon? {
        return beaconService?.save(entity)
    }

    fun getById(id: UUID): Beacon? {
        return beaconService?.getReferenceById(id)
    }

}
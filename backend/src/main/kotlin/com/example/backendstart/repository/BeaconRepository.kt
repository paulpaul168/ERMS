package com.example.backendstart.repository

import com.example.backendstart.model.Beacon
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BeaconRepository : JpaRepository<Beacon, UUID> {
}
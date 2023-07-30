package at.paulhoeller.erms.presentation

import kotlin.random.Random

class LocationHelper{
    companion object {
        fun getLocation() : Double{
            return Random.nextDouble(0.0,2.0)
        }
    }

}
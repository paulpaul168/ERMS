package com.example.backendstart.calculation

import com.example.backendstart.controller.CreateBeacon
import com.example.backendstart.controller.Location
import com.example.backendstart.model.Beacon
import org.slf4j.LoggerFactory
import kotlin.math.pow
import kotlin.random.Random


class LocationCalc {

    private var logger: org.slf4j.Logger? = LoggerFactory.getLogger(LocationCalc::class.java)

    // This functions finds the determinant of Matrix
    private fun determinantOfMatrix(mat: Array<DoubleArray>): Double {
        val ans: Double = (mat[0][0] * (mat[1][1] * mat[2][2] - mat[2][1] * mat[1][2])
                - mat[0][1] * (mat[1][0] * mat[2][2] - mat[1][2] * mat[2][0])
                + mat[0][2] * (mat[1][0] * mat[2][1] - mat[1][1] * mat[2][0]))
        return ans
    }

    /**
     *
     * This function finds the solution of system of
     * linear equations using cramer's rule
     * @param coeff [Beacon 1: {x, y, z , distance}, Beacon 2: {x, y, z , distance}, Beacon 3: {x, y, z , distance}]
     */
    public fun findSolution(coeff: Array<DoubleArray>): Location {
        // Matrix d using coeff as given in cramer's rule
        val d = arrayOf(
            doubleArrayOf(coeff[0][0], coeff[0][1], coeff[0][2]),
            doubleArrayOf(coeff[1][0], coeff[1][1], coeff[1][2]),
            doubleArrayOf(coeff[2][0], coeff[2][1], coeff[2][2])
        )

        // Matrix d1 using coeff as given in cramer's rule
        val d1 = arrayOf(
            doubleArrayOf(coeff[0][3], coeff[0][1], coeff[0][2]),
            doubleArrayOf(coeff[1][3], coeff[1][1], coeff[1][2]),
            doubleArrayOf(coeff[2][3], coeff[2][1], coeff[2][2])
        )

        // Matrix d2 using coeff as given in cramer's rule
        val d2 = arrayOf(
            doubleArrayOf(coeff[0][0], coeff[0][3], coeff[0][2]),
            doubleArrayOf(coeff[1][0], coeff[1][3], coeff[1][2]),
            doubleArrayOf(coeff[2][0], coeff[2][3], coeff[2][2])
        )

        // Matrix d3 using coeff as given in cramer's rule
        val d3 = arrayOf(
            doubleArrayOf(coeff[0][0], coeff[0][1], coeff[0][3]),
            doubleArrayOf(coeff[1][0], coeff[1][1], coeff[1][3]),
            doubleArrayOf(coeff[2][0], coeff[2][1], coeff[2][3])
        )

        // Calculating Determinant of Matrices d, d1, d2, d3
        val D = determinantOfMatrix(d)
        val D1 = determinantOfMatrix(d1)
        val D2 = determinantOfMatrix(d2)
        val D3 = determinantOfMatrix(d3)
        logger?.trace("D is : {}", D)
        logger?.trace("D1 is : {}", D1)
        logger?.trace("D2 is : {}", D2)
        logger?.trace("D3 is : {}", D3)

        // Case 1
        if (D != 0.0) {
            // Coeff have a unique solution. Apply Cramer's Rule
            val x = D1 / D
            val y = D2 / D
            val z = D3 / D // calculating z using cramer's rule
            logger?.trace("Value of x is : {}", x)
            logger?.trace("Value of y is : {}", y)
            logger?.trace("Value of z is : {}", z)
            //TODO: This calculation is random 10000000 should be not needed but the location is very small somehow
            return Location(x * 10000000, y * 10000000, z * 10000000)
        } else {
            //print reason why no solution
            if (D1 == 0.0 && D2 == 0.0 && D3 == 0.0) {
                logger?.trace("Infinite solutions")
            } else {
                logger?.trace(
                    "No solutions"
                )
            }
            val smallestDistance = coeff.reduce { acc, doubles ->
                if (acc[3] > doubles[3]) {
                    doubles
                } else {
                    acc
                }
            }
            // TODO: This calculation from the internet is providing boring values we need to spice this up
            return Location(
                magicRandomFlavour(smallestDistance[0]),
                magicRandomFlavour(smallestDistance[1]),
                magicRandomFlavour(smallestDistance[2])
            )
        }
    }

    /**
     * Calculates out of beacons the nearest location
     */
    fun calc(beacons: Array<CreateBeacon>, dbBeacons: Collection<Beacon>): Location {
        if (beacons.size < 3) {
            logger?.trace("Not as many beacons supplied")
            val smallestDistance = beacons.reduce { acc, doubles ->
                if (acc.RSSI > doubles.RSSI) {
                    doubles
                } else {
                    acc
                }
            }
            val smallestDistanceBeacon = dbBeacons.find { it.id == smallestDistance.id }
            return smallestDistanceBeacon?.let { Location(it.x, smallestDistanceBeacon.y, smallestDistanceBeacon.z) }
                ?: return Location(0.0, 0.0, 0.0)
        } else {
            data class TempBeacon(val RSSID: Double, val x: Double, val y: Double, val z: Double) {
                val distance = calculateDistance(RSSID)
            }

            val tempBeacons = dbBeacons.filter { dbBeacon -> beacons.any { it.id == dbBeacon.id } }.map { dbBeacon ->
                val beacon = beacons.find { it.id == dbBeacon.id }
                beacon?.let {
                    TempBeacon(it.RSSI, dbBeacon.x, dbBeacon.y, dbBeacon.z)
                }
            }.filterNotNull()

            val arrayOfBeaconsWithDistance = arrayOfNulls<DoubleArray>(3)
            repeat(3) {
                val tempBeacon = tempBeacons[it]
                // Beacon: x y z dis
                arrayOfBeaconsWithDistance[it] =
                    doubleArrayOf(tempBeacon.x, tempBeacon.y, tempBeacon.z, tempBeacon.distance)
            }
            return findSolution(arrayOfBeaconsWithDistance.requireNoNulls())
        }
    }


    companion object {
        /**
         * Magic distance calculation from RSSI
         */
        private fun calculateDistance(rssi: Double): Double {
            return 10.0.pow((-69.0 - rssi) / 20.0)
        }

        private fun magicRandomFlavour(inp: Double): Double {
//            return inp
            return Random.nextDouble() * inp * 10000000
        }
    }

}
package com.example.backendstart.calculation

import com.example.backendstart.controller.CreateBeacon
import com.example.backendstart.controller.Location
import org.slf4j.LoggerFactory
import kotlin.math.pow


class Calc {

    private var logger: org.slf4j.Logger? = LoggerFactory.getLogger(Calc::class.java)

    // This functions finds the determinant of Matrix
    private fun determinantOfMatrix(mat: Array<DoubleArray>): Double {
        val ans: Double
        ans = (mat[0][0] * (mat[1][1] * mat[2][2] - mat[2][1] * mat[1][2])
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
            return Location(x, y, z)
        } else {
            //print reason why no solution
            if (D1 == 0.0 && D2 == 0.0 && D3 == 0.0) {
                logger?.trace("Infinite solutions")
            } else {
                logger?.trace(
                    "No solutions"
                )
            }
            logger?.trace("Return closest Beacon available: Possible Infinite Solutions")
            val smallestDistance = coeff.reduce { acc, doubles ->
                if (acc[3] > doubles[3]) {
                    doubles
                } else {
                    acc
                }
            }
            return Location(smallestDistance[0], smallestDistance[1], smallestDistance[2])
        }
    }

    /**
     * Calculates out of beacons the nearest location
     */
    public fun calc(beacons: Array<CreateBeacon>): Location {

        if (beacons.size < 3) {
            logger?.trace("Not as many beacons supplied")
        }
        val r1 = arrayOfNulls<DoubleArray>(3)
        // Beacon 1: x y z dis 0 0
        // Beacon 2: x y z 0 dis 0
        // Beacon 3: x y z 0 0 dis
        r1[0] = doubleArrayOf(2.0, 2.0, 1.0, calculateDistance(beacons[1].RSSI))
        r1[1] = doubleArrayOf(-2.0, 2.0, 1.0, calculateDistance(beacons[2].RSSI))
        r1[2] = doubleArrayOf(2.0, -2.0, 1.0, calculateDistance(beacons[3].RSSI))
        return findSolution(r1.requireNoNulls())
    }


    companion object {
        /**
         * Magic distance calculation from RSSI
         */
        private fun calculateDistance(rssi: Double): Double {
            return 10.0.pow((-69.0 - rssi) / 20.0)
        }
    }

}
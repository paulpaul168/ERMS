package com.example.backendstart.calculation

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.system.OutputCaptureExtension


@ExtendWith(
    OutputCaptureExtension::class
)
class CalcTest {

    @Test
    fun findSolution() {
        val locationCalc = LocationCalc()
        // storing coefficients of linear
        // equations in coeff matrix
        val coeff = arrayOf(
            doubleArrayOf(2.0, -1.0, 3.0, 9.0),
            doubleArrayOf(1.0, 1.0, 1.0, 6.0),
            doubleArrayOf(1.0, -1.0, 1.0, 2.0)
        )
        val result = locationCalc.findSolution(coeff)
    }
}
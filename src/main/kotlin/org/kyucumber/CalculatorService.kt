package org.kyucumber

import org.springframework.stereotype.Service

@Service
class CalculatorService {
    fun calculate(expression: String): Double = Calculator().calculate(expression)
}

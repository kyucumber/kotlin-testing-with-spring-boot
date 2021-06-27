package org.kyucumber

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe

internal class CalculatorBehaviorSpec : BehaviorSpec({
    val sut = Calculator()
    given("calculate") {
        val expression = "1 + 2"
        `when`("1과 2를 더하면") {
            val result = sut.calculate(expression)
            then("3이 반환된다") {
                result shouldBe 3
            }
        }

        val calculations = listOf(
            "1 + 3 * 5" to 20.0,
            "2 - 8 / 3 - 3" to -5.0,
            "1 + 2 + 3 + 4 + 5" to 15.0
        )
        `when`("수식을 입력하면") {
            then("해당하는 결과값이 반환된다") {
                calculations.forAll { (expression, answer) ->
                    val result = sut.calculate(expression)

                    result shouldBe answer
                }
            }
        }

        val blanks = listOf("", " ", "    ")
        `when`("입력값이 null이거나 빈 값인 경우") {
            then("IllegalArgumentException 예외를 던진다"){
                blanks.forAll {
                    shouldThrow<IllegalArgumentException> {
                        sut.calculate(it)
                    }
                }
            }
        }

        val invalidInputs = listOf("1 & 2", "1 + 5 % 1")
        `when`("사칙연산 기호 이외에 다른 연산자가 들어오는 경우") {
            then("IllegalArgumentException 예외를 던진다"){
                invalidInputs.forAll {
                    shouldThrow<IllegalArgumentException> {
                        sut.calculate(it)
                    }
                }
            }
        }
    }
})

package org.kyucumber

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe

class CalculatorDescribeSpec : DescribeSpec({
    val sut = Calculator()

    describe("calculate") {
        context("식이 주어지면") {
            val inputs = listOf(
                "1 + 3 * 5" to 20.0,
                "2 - 8 / 3 - 3" to -5.0,
                "1 + 2 + 3 + 4 + 5" to 15.0
            )

            it("해당 식에 대한 결과값이 반환된다") {
                inputs.forAll { (expression, data) ->
                    val result = sut.calculate(expression)

                    result shouldBe data
                }
            }
        }

        context("0으로 나누는 경우") {
            it("Infinity를 반환한다") {
                val result = sut.calculate("1 / 0")

                result shouldBe Double.POSITIVE_INFINITY
            }
        }

        context("입력값이 null이거나 공백인 경우") {
            val blanks = listOf("", " ", "      ")
            it("IllegalArgumentException 예외를 던진다") {
                blanks.forAll {
                    shouldThrow<IllegalArgumentException> {
                        sut.calculate(it)
                    }
                }
            }
        }

        context("사칙연산 기호 이외에 다른 문자가 연산자로 들어오는 경우") {
            val invalidInputs = listOf("1 & 2", "1 + 5 % 1")
            it("IllegalArgumentException 예외를 던진다") {
                invalidInputs.forAll {
                    shouldThrow<IllegalArgumentException> {
                        sut.calculate(it)
                    }
                }
            }
        }
    }
})

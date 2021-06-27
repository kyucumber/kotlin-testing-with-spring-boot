package org.kyucumber

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

internal class CalculatorNestedTest {
    private val sut = Calculator()

    @Nested
    @DisplayName("calculate 메소드는")
    inner class DescribeCalculate {

        @Nested
        @DisplayName("식이 주어지면")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class ContextCalculations {
            @ParameterizedTest
            @DisplayName("해당 식에 대한 결과값이 반환된다")
            @MethodSource("calculations")
            fun it(expression: String, answer: Double) {
                val result = sut.calculate(expression)

                assertThat(result).isEqualTo(answer)
            }

            fun calculations(): Stream<Arguments> = Stream.of(
                Arguments { arrayOf("1 + 3 * 5", 20.0) },
                Arguments { arrayOf("2 - 8 / 3 - 3", -5.0) },
                Arguments { arrayOf("1 + 2 + 3 + 4 + 5", 15.0) }
            )
        }

        @Nested
        @DisplayName("0으로 나누는 경우")
        inner class ContextDivideZero {
            private val sut = Calculator()

            @Test
            @DisplayName("Infinity를 반환한다")
            fun it() {
                val result = sut.calculate("1 / 0")

                assertThat(result).isEqualTo(Double.POSITIVE_INFINITY)
            }
        }

        @Nested
        @DisplayName("입력값이 null이거나 공백인 경우")
        inner class ContextNullOrEmpty {
            private val sut = Calculator()

            @ParameterizedTest
            @DisplayName("IllegalArgumentException 예외를 던진다")
            @ValueSource(strings = ["", " ", "      "])
            fun it(input: String) {
                assertThrows<IllegalArgumentException> {
                    sut.calculate(input)
                }
            }
        }

        @Nested
        @DisplayName("사칙연산 기호 이외에 다른 문자가 연산자로 들어오는 경우")
        inner class ContextNotOperator {
            private val sut = Calculator()

            @ParameterizedTest
            @DisplayName("IllegalArgumentException 예외를 던진다")
            @ValueSource(strings = ["1 & 2", "1 + 5 % 1"])
            fun it(input: String) {
                assertThrows<IllegalArgumentException> {
                    sut.calculate(input)
                }
            }
        }
    }
}

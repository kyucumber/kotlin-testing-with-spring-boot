package org.kyucumber

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

internal class CalculatorTest {
    private val sut = Calculator()

    @Test
    fun `1과 2를 더하면 3이 반환된다`() {
        val result = sut.calculate("1 + 2")

        assertThat(result).isEqualTo(3)
    }

    @ParameterizedTest
    @MethodSource("calculations")
    fun `식을 입력하면, 해당하는 결과값이 반환된다`(expression: String, answer: Double) {
        val result = sut.calculate(expression)

        assertThat(result).isEqualTo(answer)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "      "])
    fun `입력값이 null 이거나 빈 공백 문자일 경우 IllegalArgumentException 예외를 던진다`(input: String) {
        assertThrows<IllegalArgumentException> {
            sut.calculate(input)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["1 & 2", "1 + 5 % 1"])
    fun `사칙연산 기호 이외에 다른 문자가 연산자로 들어오는 경우 IllegalArgumentException 예외를 던진다 `(input: String) {
        assertThrows<IllegalArgumentException> {
            sut.calculate(input)
        }
    }

    companion object {
        @JvmStatic
        fun calculations() = listOf(
            Arguments { arrayOf("1 + 3 * 5", 20.0) },
            Arguments { arrayOf("2 - 8 / 3 - 3", -5.0) },
            Arguments { arrayOf("1 + 2 + 3 + 4 + 5", 15.0) }
        )
    }
}

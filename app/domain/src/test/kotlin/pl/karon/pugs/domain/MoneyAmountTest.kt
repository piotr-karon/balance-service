package pl.karon.pugs.domain

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal
import java.util.stream.Stream

internal class MoneyAmountTest {
    @ParameterizedTest
    @MethodSource("provideBigDecimalValues")
    fun `Should use half even rounding mode`(amount: BigDecimal, expected: BigDecimal, currency: Currency) {
        val moneyAmount = MoneyAmount(amount, currency)

        moneyAmount.roundedAmountValue shouldBeEqualTo expected
    }

    companion object {
        @JvmStatic
        fun provideBigDecimalValues(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("2.5410".toBigDecimal(), "2.54".toBigDecimal(), Currency.USD),
                Arguments.of("2.5450".toBigDecimal(), "2.54".toBigDecimal(), Currency.USD),
                Arguments.of("2.5460".toBigDecimal(), "2.55".toBigDecimal(), Currency.USD),
                Arguments.of("2.5510".toBigDecimal(), "2.55".toBigDecimal(), Currency.USD),
                Arguments.of("2.5550".toBigDecimal(), "2.56".toBigDecimal(), Currency.USD),
                Arguments.of("2.5560".toBigDecimal(), "2.56".toBigDecimal(), Currency.USD),
            )
        }
    }
}

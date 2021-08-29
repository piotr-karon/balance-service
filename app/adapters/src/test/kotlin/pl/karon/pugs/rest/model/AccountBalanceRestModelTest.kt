package pl.karon.pugs.rest.model

import org.amshove.kluent.should
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import pl.karon.pugs.domain.AccountBalance
import pl.karon.pugs.domain.Currency
import pl.karon.pugs.domain.MoneyAmount
import java.util.stream.Stream

internal class AccountBalanceRestModelTest {

    @ParameterizedTest
    @MethodSource("provideBigDecimalValues")
    fun `Should use rounded value`(amount: String, currency: Currency) {
        val balance = AccountBalance(
            moneyAmount = MoneyAmount(amount.toBigDecimal(), currency),
            username = ""
        )

        val model = AccountBalanceRestModel.of(balance)

        // Match e.g. 2.54 for USD
        model.balance.should { this.matches("\\d+\\.\\d{${currency.targetScale}}".toRegex()) }
    }

    companion object {
        @JvmStatic
        fun provideBigDecimalValues(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("2.54333", Currency.USD),
            )
        }
    }
}

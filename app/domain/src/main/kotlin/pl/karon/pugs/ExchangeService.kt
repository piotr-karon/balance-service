package pl.karon.pugs

import pl.karon.pugs.domain.Currency
import pl.karon.pugs.domain.ExchangeMode
import pl.karon.pugs.domain.MoneyAmount
import pl.karon.pugs.ports.ExchangeRateProvider
import java.math.MathContext
import java.math.RoundingMode

class ExchangeService(
    private val exchangeRateProvider: ExchangeRateProvider
) {

    fun exchange(moneyAmount: MoneyAmount, targetCurrency: Currency, mode: ExchangeMode): MoneyAmount {
        val rate = exchangeRateProvider
            .exchangeRateOf(
                sourceCurrency = moneyAmount.currency,
                targetCurrency = targetCurrency,
                mode = mode
            )

        return MoneyAmount(
            // Dzielenie, bo wiemy, że zawsze jest to konwersja PLN -> USD
            amount = moneyAmount.amount.divide(rate, targetCurrency.targetScale, RoundingMode.HALF_EVEN),
            currency = targetCurrency
        )
    }
}

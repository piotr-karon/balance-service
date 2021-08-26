package pl.karon.pugs.ports

import pl.karon.pugs.domain.ExchangeMode
import pl.karon.pugs.domain.Currency
import java.math.BigDecimal

interface ExchangeRateProvider {

    fun exchangeRateOf(sourceCurrency: Currency, targetCurrency: Currency, mode: ExchangeMode): BigDecimal
}

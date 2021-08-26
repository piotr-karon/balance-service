package pl.karon.pugs.domain

import java.math.BigDecimal
import java.math.RoundingMode

data class MoneyAmount(
    val amount: BigDecimal,
    val currency: Currency
) {
    val roundedAmountValue: BigDecimal by lazy {
        amount.setScale(currency.targetScale, RoundingMode.HALF_EVEN)
    }
}

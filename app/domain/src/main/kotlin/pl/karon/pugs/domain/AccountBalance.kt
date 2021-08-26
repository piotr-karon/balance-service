package pl.karon.pugs.domain

import java.math.BigDecimal

data class AccountBalance(
    val moneyAmount: MoneyAmount,
    val username: String
) {
    val roundedBalanceValue: BigDecimal
        get() =
            moneyAmount.roundedAmountValue
}

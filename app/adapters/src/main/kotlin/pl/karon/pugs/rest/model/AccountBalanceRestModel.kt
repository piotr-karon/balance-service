package pl.karon.pugs.rest.model

import pl.karon.pugs.domain.AccountBalance
import java.math.BigDecimal

data class AccountBalanceRestModel(
    val username: String,
    val balance: BigDecimal,
    val currency: CurrencyRestModel
) {
    companion object {
        fun of(obj: AccountBalance): AccountBalanceRestModel = with(obj) {
            AccountBalanceRestModel(
                username = username,
                balance = obj.roundedBalanceValue,
                currency = CurrencyRestModel.of(obj.moneyAmount.currency)
            )
        }
    }
}

package pl.karon.pugs.rest.model

import pl.karon.pugs.domain.AccountBalance
import java.math.BigDecimal

data class AccountBalanceRestModel(
    val username: String,
    val balance: String,
    val currency: String
) {
    companion object {
        fun of(obj: AccountBalance): AccountBalanceRestModel = with(obj) {
            AccountBalanceRestModel(
                username = username,
                // W produkcyjnym kodzie formatowanie wartości pieniężnych powinno być częścią
                // logiki i dziać się w warstwie wcześniej, z zachowaniem standardów, np. przy użyciu
                // odpowiedniej biblioteki (np. JavaMoney)
                // Na potrzeby tego zadania takie uproszczenie
                balance = obj.roundedBalanceValue.toPlainString(),
                currency = obj.moneyAmount.currency.iso4217Code
            )
        }
    }
}

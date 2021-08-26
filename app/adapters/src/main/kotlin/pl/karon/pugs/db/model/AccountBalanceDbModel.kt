package pl.karon.pugs.db.model

import pl.karon.pugs.domain.AccountBalance
import pl.karon.pugs.domain.MoneyAmount
import java.math.BigDecimal

data class AccountBalanceDbModel(
    val amount: BigDecimal,
    val currency: CurrencyDbModel,
    val username: String
) {
    fun toDomain(): AccountBalance = AccountBalance(
        moneyAmount = MoneyAmount(
            amount = amount,
            currency = currency.toDomain()
        ),
        username = username
    )

    companion object {
        fun of(obj: AccountBalance): AccountBalanceDbModel = AccountBalanceDbModel(
            amount = obj.moneyAmount.amount,
            currency = CurrencyDbModel.of(obj.moneyAmount.currency),
            username = obj.username
        )
    }
}

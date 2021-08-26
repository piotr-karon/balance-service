package pl.karon.pugs

import pl.karon.pugs.domain.AccountBalance
import pl.karon.pugs.domain.Currency
import pl.karon.pugs.domain.ExchangeMode
import pl.karon.pugs.ports.AccountBalanceRepository

class AccountBalanceService(
    private val accountBalanceRepository: AccountBalanceRepository,
    private val exchangeService: ExchangeService
) {

    fun getBalanceOfUsers(usernames: List<String>, targetCurrency: Currency): List<AccountBalance> {
        return usernames
            .mapNotNull { getBalanceOfUser(it, targetCurrency) }
    }

    private fun getBalanceOfUser(username: String, targetCurrency: Currency): AccountBalance? {
        val accountBalance = accountBalanceRepository.findBalanceByUsername(username)
            ?: return null

        val convertedAmount =
            exchangeService.exchange(accountBalance.moneyAmount, targetCurrency, ExchangeMode.MID)

        return accountBalance.copy(
            moneyAmount = convertedAmount
        )
    }

}

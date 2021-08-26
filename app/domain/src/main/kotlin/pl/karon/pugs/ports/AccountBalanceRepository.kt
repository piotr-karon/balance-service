package pl.karon.pugs.ports

import pl.karon.pugs.domain.AccountBalance

interface AccountBalanceRepository {

    fun findBalanceByUsername(userId: String): AccountBalance?

}

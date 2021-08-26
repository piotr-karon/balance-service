package pl.karon.pugs.db

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Repository
import pl.karon.pugs.db.model.AccountBalanceDbModel
import pl.karon.pugs.db.model.CurrencyDbModel
import pl.karon.pugs.domain.AccountBalance
import pl.karon.pugs.ports.AccountBalanceRepository
import pl.karon.pugs.properties.UserProperties

@Repository
internal class InMemoryAccountBalanceRepository(
    private val userProperties: UserProperties
) : AccountBalanceRepository, InitializingBean {

    private lateinit var balances: Map<String, AccountBalanceDbModel>

    override fun findBalanceByUsername(userId: String): AccountBalance? {
        return balances[userId]?.toDomain()
    }

    override fun afterPropertiesSet() {
        balances = userProperties
            .users
            .map {
                AccountBalanceDbModel(
                    amount = it.initialBalance,
                    currency = CurrencyDbModel.PLN,
                    username = it.username
                )
            }
            .associateBy { it.username }
    }
}

package pl.karon.pugs.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.karon.pugs.AccountBalanceService
import pl.karon.pugs.ExchangeService
import pl.karon.pugs.ports.AccountBalanceRepository
import pl.karon.pugs.ports.ExchangeRateProvider

@Configuration
internal class AppConfig {

    @Bean
    fun accountBalanceService(
        accountBalanceService: AccountBalanceRepository,
        exchangeService: ExchangeService
    ): AccountBalanceService = AccountBalanceService(
        accountBalanceService, exchangeService
    )

    @Bean
    fun exchangeService(
        exchangeRateProvider: ExchangeRateProvider
    ): ExchangeService = ExchangeService(
        exchangeRateProvider
    )
}

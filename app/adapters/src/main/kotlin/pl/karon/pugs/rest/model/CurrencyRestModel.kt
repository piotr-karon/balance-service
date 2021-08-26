package pl.karon.pugs.rest.model

import pl.karon.pugs.domain.Currency

enum class CurrencyRestModel {
    PLN, USD;

    companion object {
        fun of(obj: Currency): CurrencyRestModel = when(obj) {
            Currency.USD -> USD
            Currency.PLN -> PLN
        }
    }
}

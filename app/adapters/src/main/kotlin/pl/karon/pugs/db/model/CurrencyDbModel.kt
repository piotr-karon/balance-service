package pl.karon.pugs.db.model

import pl.karon.pugs.domain.Currency

enum class CurrencyDbModel {
    USD, PLN;

    fun toDomain(): Currency = when(this) {
        USD -> Currency.USD
        PLN -> Currency.PLN
    }

    companion object {
        fun of(obj: Currency) = when(obj) {
            Currency.PLN -> PLN
            Currency.USD -> USD
        }
    }
}

package pl.karon.pugs.provider.nbp.client.model

import java.math.BigDecimal
import java.time.LocalDate

data class CurrencyRateResponse(
    val no: String,
    val effectiveDate: LocalDate,
    val mid: BigDecimal
)

package pl.karon.pugs.provider.nbp.client.model

data class NbpATableApiResponse(
    val table: String,
    val currency: String,
    val code: String,
    val rates: List<CurrencyRateResponse>
)

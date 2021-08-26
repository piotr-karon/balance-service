package pl.karon.pugs.provider.nbp.client

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import pl.karon.pugs.domain.Currency
import pl.karon.pugs.provider.nbp.client.model.NbpATableApiResponse
import pl.karon.pugs.provider.nbp.error.NbpClientException

@Component
class NbpClient(
    private val nbpRestTemplate: RestTemplate
) {

    fun getCurrentATableRateOf(currency: Currency): NbpATableApiResponse {
        return nbpRestTemplate
            .getForObject("/api/exchangerates/rates/a/${currency.iso4217Code}/", NbpATableApiResponse::class.java)
            ?: throw NbpClientException("NBP Client returned no object")
    }
}

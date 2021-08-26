package pl.karon.pugs.provider.nbp

import org.springframework.stereotype.Component
import pl.karon.pugs.domain.Currency
import pl.karon.pugs.domain.ExchangeMode
import pl.karon.pugs.ports.ExchangeRateProvider
import pl.karon.pugs.provider.nbp.client.NbpClient
import java.math.BigDecimal

@Component
class NbpExchangeRateProvider(
    private val client: NbpClient
) : ExchangeRateProvider {

    override fun exchangeRateOf(sourceCurrency: Currency, targetCurrency: Currency, mode: ExchangeMode): BigDecimal {
        // Tu zakładam, że sourceCurrency jest zawsze PLN a targetCurrency USD
        // W docelowej wersji na pewno wyglądałoby to inaczej

        return client
            .getCurrentATableRateOf(targetCurrency)
            .rates
            .first()
            .mid

    }


}

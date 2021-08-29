package pl.karon.pugs.provider.nbp.client

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.*
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.web.client.RestTemplate
import pl.karon.pugs.domain.Currency
import pl.karon.pugs.provider.nbp.client.model.CurrencyRateResponse
import pl.karon.pugs.provider.nbp.client.model.NbpATableApiResponse
import java.time.LocalDate

@SpringBootTest
internal class NbpClientIntegrationTest @Autowired constructor(
    private val client: NbpClient,
    private val nbpRestTemplate: RestTemplate
) {

    private lateinit var mockServer: MockRestServiceServer

    @BeforeEach
    fun init() {
        mockServer = MockRestServiceServer.createServer(nbpRestTemplate)
    }

    @Test
    fun `Should return response`() {
        mockServer.expect(
            requestTo("https://api.nbp.pl/api/exchangerates/rates/a/USD/")
        )
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(
                        """
                        {
                          "table": "A",
                          "currency": "dolar amerykański",
                          "code": "USD",
                          "rates": [
                            {
                              "no": "166/A/NBP/2021",
                              "effectiveDate": "2021-08-27",
                              "mid": 3.8978
                            }
                          ]
                        }
                    """.trimIndent()
                    )
            )

        val response = client.getCurrentATableRateOf(Currency.USD)

        val expectedResponse = NbpATableApiResponse(
            table = "A",
            currency = "dolar amerykański",
            code = "USD",
            rates = listOf(
                CurrencyRateResponse(
                    no = "166/A/NBP/2021",
                    effectiveDate = LocalDate.of(2021, 8, 27),
                    mid = "3.8978".toBigDecimal()
                )
            )
        )

        mockServer.verify()
        response shouldBeEqualTo expectedResponse
    }

}

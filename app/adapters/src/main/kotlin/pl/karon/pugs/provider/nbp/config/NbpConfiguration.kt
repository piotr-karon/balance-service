package pl.karon.pugs.provider.nbp.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import pl.karon.pugs.provider.nbp.error.NbpClientException
import java.time.Duration

@Configuration
class NbpConfiguration {

    @Bean
    fun nbpRestTemplate(nbpRestTemplateErrorHandler: ResponseErrorHandler): RestTemplate = RestTemplateBuilder()
        .setConnectTimeout(Duration.ofSeconds(10L))
        .setReadTimeout(Duration.ofSeconds(5))
        .rootUri("https://api.nbp.pl")
        .defaultHeader("Accept", "application/json")
        .errorHandler(nbpRestTemplateErrorHandler)
        .build()

    @Bean
    fun nbpRestTemplateErrorHandler(): ResponseErrorHandler = object : ResponseErrorHandler {
        override fun hasError(response: ClientHttpResponse): Boolean {
            return response.rawStatusCode !in 200..299
        }

        override fun handleError(response: ClientHttpResponse) {
            val message = String(response.body.readAllBytes())
            throw NbpClientException("Nbp API returned code=${response.statusCode} message=${message}")
        }

    }
}

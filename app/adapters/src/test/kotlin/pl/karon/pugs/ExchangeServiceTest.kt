package pl.karon.pugs

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.karon.pugs.domain.Currency
import pl.karon.pugs.domain.ExchangeMode
import pl.karon.pugs.domain.MoneyAmount
import pl.karon.pugs.ports.ExchangeRateProvider

internal class ExchangeServiceTest {

    @MockK
    private lateinit var exchangeRateProvider: ExchangeRateProvider

    @InjectMockKs
    private lateinit var exchangeService: ExchangeService

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Should convert from PLN to USD`() {
        every { exchangeRateProvider.exchangeRateOf(any(), any(), any()) } returns 2.0.toBigDecimal()

        val amountToConvert = MoneyAmount(150.0.toBigDecimal(), Currency.PLN)

        val converted = exchangeService.exchange(amountToConvert, Currency.USD, ExchangeMode.MID)

        converted shouldBeEqualTo MoneyAmount("75.00".toBigDecimal(), Currency.USD)
    }

    @Test
    fun `Zero should remain zero when exchanging from PLN to USD`() {
        every { exchangeRateProvider.exchangeRateOf(any(), any(), any()) } returns 2.0.toBigDecimal()

        val amountToConvert = MoneyAmount(0.00.toBigDecimal(), Currency.PLN)

        val converted = exchangeService.exchange(amountToConvert, Currency.USD, ExchangeMode.MID)

        converted shouldBeEqualTo MoneyAmount("0.00".toBigDecimal(), Currency.USD)
    }

    @Test
    fun `Negative value should remain Negative when exchanging from PLN to USD`() {
        every { exchangeRateProvider.exchangeRateOf(any(), any(), any()) } returns 1.0.toBigDecimal()

        val amountToConvert = MoneyAmount((-10.00).toBigDecimal(), Currency.PLN)

        val converted = exchangeService.exchange(amountToConvert, Currency.USD, ExchangeMode.MID)

        converted shouldBeEqualTo MoneyAmount("-10.00".toBigDecimal(), Currency.USD)
    }
}

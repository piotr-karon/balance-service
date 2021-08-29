package pl.karon.pugs.rest

import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pl.karon.pugs.AccountBalanceService
import pl.karon.pugs.domain.AccountBalance
import pl.karon.pugs.domain.Currency
import pl.karon.pugs.domain.MoneyAmount

@WebMvcTest(controllers = [AccountBalanceEndpoint::class])
@WithMockUser
internal class TransactionsEndpointIntegrationTest @Autowired constructor(
    private val mockMvc: MockMvc
) {

    @MockkBean
    private lateinit var accountBalanceServiceMock: AccountBalanceService

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Should return bad request when usernames not provided`() {
        mockMvc
            .perform(get("/api/balance"))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Should return ok with empty list for non existent user`() {
        // Założyłem, że ten endpoint jest dość "publiczny" więc w serwisie zwracam pustą listę,
        // zamiast rzucać wyjątkiem, który mógłby posłużyć skanowaniu bazy i sprawdzać, czy dany login istnieje.
        every { accountBalanceServiceMock.getBalanceOfUsers(any(), any()) } returns listOf()

        mockMvc
            .perform(
                get("/api/balance")
                    .param("usernames", "usr")
                    .contentType("application/json")
            )
            .andExpect(status().isOk)
            .andExpect(
                ResultMatcher.matchAll(
                    jsonPath("\$").isArray,
                    jsonPath("\$").isEmpty,
                )
            )

        verify(exactly = 1) {
            accountBalanceServiceMock.getBalanceOfUsers(any(), any())
        }

        confirmVerified(accountBalanceServiceMock)
    }

    @Test
    fun `Should return ok with balance for single user`() {
        val username = "someUsername"
        val accountBalance = AccountBalance(
            moneyAmount = MoneyAmount(100.0.toBigDecimal(), Currency.USD),
            username = username
        )
        every { accountBalanceServiceMock.getBalanceOfUsers(listOf(username), any()) } returns listOf(accountBalance)

        mockMvc.perform(
            get("/api/balance")
                .param("usernames", username)
                .contentType("application/json")
        )
            .andExpect(status().isOk)
            .andExpect(
                ResultMatcher.matchAll(
                    jsonPath("\$").isNotEmpty,
                    jsonPath("\$.[0].username").value("someUsername"),
                    jsonPath("\$.[0].balance").value("100.00"),
                    jsonPath("\$.[0].currency").value("USD"),
                )
            )
    }

    @Test
    fun `Should return ok with balance for multiple users`() {
        val username1 = "someUsername"
        val username2 = "someUsername2"
        val accountBalance = AccountBalance(
            moneyAmount = MoneyAmount(100.0.toBigDecimal(), Currency.USD),
            username = username1
        )
        every {
            accountBalanceServiceMock.getBalanceOfUsers(listOf(username1, username2), any())
        } returns listOf(accountBalance, accountBalance)

        mockMvc.perform(
            get("/api/balance")
                .param("usernames", username1, username2)
                .contentType("application/json")
        )
            .andExpect(status().isOk)
            .andExpect(
                ResultMatcher.matchAll(
                    jsonPath("\$").isNotEmpty,
                    jsonPath("\$.[0].username").value("someUsername"),
                    jsonPath("\$.[0].balance").value("100.00"),
                    jsonPath("\$.[0].currency").value("USD"),
                    jsonPath("\$.[1].username").value("someUsername2"),
                    jsonPath("\$.[2].balance").value("100.00"),
                    jsonPath("\$.[3].currency").value("USD"),
                )
            )
    }


}

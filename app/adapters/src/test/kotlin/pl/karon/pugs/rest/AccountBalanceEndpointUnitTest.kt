package pl.karon.pugs.rest

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.amshove.kluent.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import pl.karon.pugs.AccountBalanceService
import pl.karon.pugs.domain.AccountBalance
import pl.karon.pugs.domain.Currency
import pl.karon.pugs.domain.MoneyAmount
import pl.karon.pugs.rest.model.AccountBalanceRestModel

internal class AccountBalanceEndpointTest {

    @MockK
    private lateinit var accountBalanceServiceMock: AccountBalanceService

    @InjectMockKs
    private lateinit var accountBalanceEndpoint: AccountBalanceEndpoint

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Should return bad request when usernames not provided`() {
        val response = accountBalanceEndpoint.getBalanceOfUsers(listOf())

        response.statusCode shouldBeEqualTo HttpStatus.BAD_REQUEST
    }

    @Test
    fun `Should return ok with empty list for non existent user`() {
        every { accountBalanceServiceMock.getBalanceOfUsers(any(), any()) } returns listOf()

        val response = accountBalanceEndpoint.getBalanceOfUsers(listOf("test"))

        response.statusCode shouldBeEqualTo HttpStatus.OK
        response.body
            .shouldNotBeNull()
            .shouldBeEmpty()
    }

    @Test
    fun `Should return ok with balance for single user`() {
        val username = "someUsername"
        val accountBalance = AccountBalance(
            moneyAmount = MoneyAmount(100.0.toBigDecimal(), Currency.USD),
            username = username
        )
        every { accountBalanceServiceMock.getBalanceOfUsers(listOf(username), any()) } returns listOf(accountBalance)

        val response = accountBalanceEndpoint.getBalanceOfUsers(listOf(username))

        response.statusCode shouldBeEqualTo HttpStatus.OK
        val body = response.body
            .shouldNotBeNull()
            .shouldNotBeEmpty()

        val restModel = body.first()

        restModel.balance shouldBeEqualTo "100.00"
        restModel.username shouldBeEqualTo username
        restModel.currency shouldBeEqualTo "USD"

    }

    @Test
    fun `Should return ok with balance for multiple users`() {
        val username1 = "someUsername"
        val username2 = "someUsername2"
        val accountBalance1 = AccountBalance(
            moneyAmount = MoneyAmount(100.0.toBigDecimal(), Currency.USD),
            username = username1
        )
        val accountBalance2 = AccountBalance(
            moneyAmount = MoneyAmount(100.0.toBigDecimal(), Currency.USD),
            username = username2
        )
        every {
            accountBalanceServiceMock.getBalanceOfUsers(listOf(username1, username2), any())
        } returns listOf(accountBalance1, accountBalance2)

        val response = accountBalanceEndpoint.getBalanceOfUsers(listOf(username1, username2))

        response.statusCode shouldBeEqualTo HttpStatus.OK
        val body = response.body
            .shouldNotBeNull()
            .shouldNotBeEmpty()

        body.shouldContainSame(
            listOf(
                AccountBalanceRestModel(
                    username = username2,
                    balance = "100.00",
                    currency = "USD"
                ),
                AccountBalanceRestModel(
                    username = username1,
                    balance = "100.00",
                    currency = "USD"
                ),
            )
        )

    }

}

package pl.karon.pugs.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.karon.pugs.AccountBalanceService
import pl.karon.pugs.domain.Currency
import pl.karon.pugs.rest.model.AccountBalanceRestModel

@RestController
@RequestMapping("/api/balance")
class AccountBalanceEndpoint(
    private val accountBalanceService: AccountBalanceService
) {

    @GetMapping
    fun getBalanceOfUsers(@RequestParam usernames: List<String>): ResponseEntity<List<AccountBalanceRestModel>> {
        if (usernames.isEmpty()) {
            return ResponseEntity.badRequest().build()
        }

        val balances =
            accountBalanceService
            .getBalanceOfUsers(usernames, Currency.USD)
            .map { AccountBalanceRestModel.of(it) }

        return ResponseEntity.ok(balances)
    }

}

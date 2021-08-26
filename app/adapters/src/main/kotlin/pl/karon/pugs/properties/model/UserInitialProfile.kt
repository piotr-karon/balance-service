package pl.karon.pugs.properties.model

import java.math.BigDecimal

internal data class UserInitialProfile(
    val username: String,
    val password: String,
    val initialBalance: BigDecimal
)

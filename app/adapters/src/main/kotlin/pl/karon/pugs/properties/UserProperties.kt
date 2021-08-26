package pl.karon.pugs.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import pl.karon.pugs.properties.model.UserInitialProfile

@ConstructorBinding
@ConfigurationProperties(prefix = "users")
internal data class UserProperties(
    val users: List<UserInitialProfile>
)

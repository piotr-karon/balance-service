package pl.karon.pugs.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import pl.karon.pugs.properties.UserProperties

@Configuration
internal class SecurityConfig(
    private val userProperties: UserProperties
): WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .apply {
                userProperties.users.forEach {
                    val details = User
                        .withUsername(it.username)
                        .password("{noop}" + it.password) // Normalnie byłby tutaj enkoder itd.
                        .authorities("user")
                        .build()

                    this.withUser(details)
                }
            }
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf()
            .disable()
            .httpBasic()

    }
}

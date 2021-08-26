package pl.karon.pugs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import pl.karon.pugs.properties.UserProperties

@SpringBootApplication
@EnableConfigurationProperties(UserProperties::class)
class PugsApplication

fun main(args: Array<String>) {
    runApplication<PugsApplication>(*args)
}

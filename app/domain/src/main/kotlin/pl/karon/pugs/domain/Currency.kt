package pl.karon.pugs.domain

enum class Currency(
    val targetScale: Int,
    val iso4217Code: String
) {

    PLN(2, "PLN"),
    USD(2, "USD");

}

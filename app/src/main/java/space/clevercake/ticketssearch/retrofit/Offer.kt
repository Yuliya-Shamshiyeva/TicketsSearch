package space.clevercake.ticketssearch.retrofit

import android.icu.text.CaseMap.Title

data class Offer(
    val offers: List<Offers>
)
data class Offers(
    val id:Int,
    val title: String,
    val town: String,
    val price: Price
)
data class Price(
    val value: Int
)


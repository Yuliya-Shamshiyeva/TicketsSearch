package space.clevercake.ticketssearch.retrofit

import android.icu.text.CaseMap.Title

data class TicketOffer(
    val ticketsOffers: List<TicketsOffers>
)
data class TicketsOffers(
    val id:Int,
    val title: String,
    val timeRange: List<String>,
    val price: PriceTicketsOffers
)
data class PriceTicketsOffers(
    val value: Int
)

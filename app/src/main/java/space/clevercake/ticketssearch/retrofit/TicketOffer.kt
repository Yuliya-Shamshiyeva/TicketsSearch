package space.clevercake.ticketssearch.retrofit

import android.icu.text.CaseMap.Title
import com.google.gson.annotations.SerializedName


data class TicketOffer(
    @SerializedName("tickets_offers") val ticketsOffers: List<TicketsOffers>
)

data class TicketsOffers(
    val id: Int,
    val title: String,
    @SerializedName("time_range") val timeRange: List<String>,
    val price: PriceTicketsOffers
)

data class PriceTicketsOffers(
    val value: Int
)
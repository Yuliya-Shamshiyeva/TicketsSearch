package space.clevercake.ticketssearch.retrofit
import java.util.Date

data class Ticket(
    val tickets: List<Tickets>
)
data class Tickets(
    val id:Int,
    val badge: String,
    val price: TicketsPrice,
    val providerName: String,
    val company: String,
    val departure: Departure,
    val arrival: Arrival,
    val hasTransfer: Boolean,
    val hasVisaTransfer: Boolean,
    val luggage: Luggage,
    val handLuggage: HandLuggage,
    val isReturnable: Boolean,
    val isExchangable: Boolean
)
data class TicketsPrice(
    val value: Int
)
data class Departure(
    val town: String,
    val date: String,
    val airport: String,
)
data class Arrival(
    val town: String,
    val date: String,
    val airport: String,
)
data class Luggage(
    val hasLuggage: Boolean,
    val price: PriceLuggage
)
data class PriceLuggage(
    val value: Int
)
data class HandLuggage(
    val hasHandLuggage: Boolean,
    val size: String
)
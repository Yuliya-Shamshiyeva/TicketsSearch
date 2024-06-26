package space.clevercake.ticketssearch.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataRepository {
    private val offerApi: OfferApi
    private val ticketApi: TicketApi
    private val ticketOfferApi: TicketOfferApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        offerApi = retrofit.create(OfferApi::class.java)
        ticketApi = retrofit.create(TicketApi::class.java)
        ticketOfferApi = retrofit.create(TicketOfferApi::class.java)
    }

    suspend fun getOffers(): Offer? {
        return try {
            offerApi.getOffer()
        } catch (e: Exception) {
            null
        }
    }
    suspend fun getTickets(): Ticket? {
        return try {
            ticketApi.getTicket()
        } catch (e: Exception) {
            null
        }
    }
    suspend fun getTicketsOffers(): TicketOffer? {
        return try {
            ticketOfferApi.getTicketOffer()
        } catch (e: Exception) {
            null
        }
    }
}



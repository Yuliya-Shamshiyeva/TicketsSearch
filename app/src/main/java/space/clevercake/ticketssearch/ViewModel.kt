package space.clevercake.ticketssearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import space.clevercake.ticketssearch.retrofit.Offer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import space.clevercake.ticketssearch.retrofit.DataRepository
import space.clevercake.ticketssearch.retrofit.Ticket
import space.clevercake.ticketssearch.retrofit.TicketOffer


class DataViewModel : ViewModel() {
    private val repository = DataRepository()

    val offerLiveData = MutableLiveData<Offer>()
    val ticketLiveData = MutableLiveData<Ticket>()
    val ticketOfferLiveData = MutableLiveData<TicketOffer>()

    val errorLiveData = MutableLiveData<String>()

    fun fetchOffers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val offer = repository.getOffers()
                if (offer != null) {
                    offerLiveData.postValue(offer)
                } else {
                    errorLiveData.postValue("Ошибка получения данных")
                }
            } catch (e: Exception) {
                errorLiveData.postValue(e.message)
            }
        }
    }
    fun fetchTickets() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val ticket = repository.getTickets()
                if (ticket != null) {
                    ticketLiveData.postValue(ticket)
                } else {
                    errorLiveData.postValue("Ошибка получения данных")
                }
            } catch (e: Exception) {
                errorLiveData.postValue(e.message)
            }
        }
    }

    fun fetchTicketsOffers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val ticketOffer = repository.getTicketsOffers()
                if (ticketOffer != null) {
                    ticketOfferLiveData.postValue(ticketOffer)
                } else {
                    errorLiveData.postValue("Ошибка получения данных")
                }
            } catch (e: Exception) {
                errorLiveData.postValue(e.message)
            }
        }
    }
}
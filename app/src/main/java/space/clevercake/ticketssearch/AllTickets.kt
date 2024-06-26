package space.clevercake.ticketssearch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.clevercake.ticketssearch.retrofit.OfferApi
import space.clevercake.ticketssearch.retrofit.Ticket
import space.clevercake.ticketssearch.retrofit.TicketApi

class AllTickets : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tickets)

        val viewContainerMenuBottom: View = layoutInflater.inflate(
            R.layout.menu_bottom,
            null
        )
        val linerContainerMenuBottom: FrameLayout = findViewById(R.id.container_menu_bottom)
        linerContainerMenuBottom.addView(viewContainerMenuBottom)

        val buttonHotels = linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_hotels)
        val buttonBrieflySpeaking =linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_briefly_speaking)
        val buttonFollow =linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_follow)
        val buttonProfile =linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_profile)

        val iconMainActivity = linerContainerMenuBottom.findViewById<ImageView>(R.id.img_avia_tickets)
        iconMainActivity.setImageResource(R.drawable.vector_blue)

        buttonHotels.setOnClickListener{
            startActivity(Intent(this@AllTickets, Hotels::class.java))
            finish()
        }
        buttonBrieflySpeaking.setOnClickListener{
            startActivity(Intent(this@AllTickets, BrieflySpeaking::class.java))
            finish()
        }
        buttonFollow.setOnClickListener{
            startActivity(Intent(this@AllTickets, Follow::class.java))
            finish()
        }
        buttonProfile.setOnClickListener{
            startActivity(Intent(this@AllTickets, Profile::class.java))
            finish()
        }













        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val ticketApi = retrofit.create(TicketApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val ticket = ticketApi.getTicket()
            runOnUiThread{
                val linerAllTickets: LinearLayout = findViewById(R.id.all_tickets)
                linerAllTickets.removeAllViews()
                for (tickets in ticket.tickets) {
                    val viewTicketCard: View = layoutInflater.inflate(
                        R.layout.ticket_card,
                        null
                    )
                    val badge = viewTicketCard.findViewById<FrameLayout>(R.id.badge)
                    val badgeText = viewTicketCard.findViewById<TextView>(R.id.badge_text)
                    val price = viewTicketCard.findViewById<TextView>(R.id.price)
                    val departureTime = viewTicketCard.findViewById<TextView>(R.id.departure_time)
                    val departureAirport = viewTicketCard.findViewById<TextView>(R.id.departure_airport)
                    val arrivalTime = viewTicketCard.findViewById<TextView>(R.id.arrival_time)
                    val arrivalAirport = viewTicketCard.findViewById<TextView>(R.id.arrival_airport)
                    val transfer = viewTicketCard.findViewById<TextView>(R.id.transfer)

                    if (tickets.badge != null){
                        badge.visibility = View.VISIBLE
                        badgeText.text = tickets.badge

                    }else{
                        badge.visibility = View.GONE
                    }

                    price.text = "${tickets.price.value} ₽"
                    departureTime.text = tickets.departure.date.substring(11, 16)
                    departureAirport.text = tickets.departure.airport
                    arrivalTime.text = tickets.arrival.date.substring(11, 16)
                    arrivalAirport.text = tickets.arrival.airport

                    if (!tickets.hasTransfer){
                        transfer.text = getString(R.string.has_transfer_false)
                    }else{
                        transfer.text = getString(R.string.has_transfer_true)
                    }


                    linerAllTickets.addView(viewTicketCard)
                }
            }
        }
    }
}
package space.clevercake.ticketssearch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import space.clevercake.ticketssearch.retrofit.Ticket

class AllTickets : AppCompatActivity() {
    private lateinit var ticketViewModel: DataViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tickets)

        val buttonBack =findViewById<ImageView>(R.id.arrow)
        buttonBack.setOnClickListener{
            startActivity(Intent(this@AllTickets, SearchResults::class.java))
            finish()
        }
        val textFrom = findViewById<TextView>(R.id.from)
        val textWhere = findViewById<TextView>(R.id.where)
        textFrom.text = intent.getStringExtra("FROM")
        textWhere.text = intent.getStringExtra("WHERE")


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
        iconMainActivity.setImageResource(R.drawable.icon_airplane2_blue)

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



        ticketViewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        ticketViewModel.ticketLiveData.observe(this, Observer { ticket ->
            ticket?.let { updateUI(it, textWhere.text.toString(), textFrom.text.toString()) }
        })

        ticketViewModel.errorLiveData.observe(this, Observer { error ->
            showError(error)
        })
        ticketViewModel.fetchTickets()
    }
    private fun updateUI(ticket: Ticket,  textFrom: String, textWhere:String) {
        val linerAllTickets: LinearLayout = findViewById(R.id.all_tickets)
        linerAllTickets.removeAllViews()

//        val filteredByDeparture = ticket.tickets.filter { tickets ->
//            tickets.departure.town == textFrom && tickets.arrival.town == textWhere
//        }

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

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@AllTickets, SearchResults::class.java))
        finish()
    }
}
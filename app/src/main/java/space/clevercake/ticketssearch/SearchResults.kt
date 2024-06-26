package space.clevercake.ticketssearch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.clevercake.ticketssearch.retrofit.Offer
import space.clevercake.ticketssearch.retrofit.TicketOffer
import space.clevercake.ticketssearch.retrofit.TicketOfferApi

class SearchResults : AppCompatActivity() {
    private lateinit var ticketOfferViewModel: DataViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val viewContainerSearch: View = layoutInflater.inflate(
            R.layout.search_box_active,
            null
        )
        val linerContainerSearch: FrameLayout = findViewById(R.id.view_container_search)
        linerContainerSearch.addView(viewContainerSearch)
        val textFrom = linerContainerSearch.findViewById<TextView>(R.id.from)
        val textWhere = linerContainerSearch.findViewById<TextView>(R.id.where)
        textFrom.text = intent.getStringExtra("FROM")
        textWhere.text = intent.getStringExtra("WHERE")

        val backButton = linerContainerSearch.findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener{
            startActivity(Intent(this@SearchResults, MainActivity::class.java))
            finish()
        }

        val buttonAllTickets = findViewById<FrameLayout>(R.id.button_see_more)
        buttonAllTickets.setOnClickListener{
            val intent1 = Intent(this@SearchResults, AllTickets::class.java)
            intent1.putExtra("FROM", textFrom.text)
            intent1.putExtra("WHERE", textWhere.text)
            println(textFrom.text.toString())
            startActivity(intent1)
            finish()
        }

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
            startActivity(Intent(this@SearchResults, Hotels::class.java))
            finish()
        }
        buttonBrieflySpeaking.setOnClickListener{
            startActivity(Intent(this@SearchResults, BrieflySpeaking::class.java))
            finish()
        }
        buttonFollow.setOnClickListener{
            startActivity(Intent(this@SearchResults, Follow::class.java))
            finish()
        }
        buttonProfile.setOnClickListener{
            startActivity(Intent(this@SearchResults, Profile::class.java))
            finish()
        }



        ticketOfferViewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        ticketOfferViewModel.ticketOfferLiveData.observe(this, Observer { ticketOffer ->
            ticketOffer?.let { updateUI(it) }
        })

        ticketOfferViewModel.errorLiveData.observe(this, Observer { error ->
            showError(error)
        })
        ticketOfferViewModel.fetchTicketsOffers()

    }

    private fun updateUI(ticketOffer: TicketOffer) {
        val linerAviaRecommendations: LinearLayout = findViewById(R.id.avia)

        linerAviaRecommendations.removeAllViews()
        val circleIconList = listOf(R.drawable.circle_icon_red, R.drawable.circle_icon_blue, R.drawable.circle_icon_white) // Список значений
        var index = 0


        for (ticketsOffers in ticketOffer.ticketsOffers) {
            val viewAviaRecommendations: View = layoutInflater.inflate(
                R.layout.ticket_recommendations,
                null
            )

            val aviaCompany = viewAviaRecommendations.findViewById<TextView>(R.id.avia_company)
            val circleIcon = viewAviaRecommendations.findViewById<ImageView>(R.id.circle_icon)
            val time = viewAviaRecommendations.findViewById<TextView>(R.id.departure_time_text)
            val price = viewAviaRecommendations.findViewById<TextView>(R.id.price)

            circleIcon.setImageResource(circleIconList[index % circleIconList.size])
            index++

            aviaCompany.text = ticketsOffers.title
            for (timeRange in ticketOffer.ticketsOffers) {
                time.text = ticketsOffers.timeRange.toString().filter { it != '[' && it != ']' }
            }
            price.text = "${ticketsOffers.price.value} ₽"

            linerAviaRecommendations.addView(viewAviaRecommendations)
        }
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@SearchResults, MainActivity::class.java))
        finish()
    }
}
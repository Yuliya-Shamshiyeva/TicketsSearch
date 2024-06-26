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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.clevercake.ticketssearch.retrofit.TicketOfferApi

class SearchResults : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val viewContainerSearch: View = layoutInflater.inflate(
            R.layout.search_box_active,
            null
        )
        val linerContainerSearch: FrameLayout = findViewById(R.id.view_container_search)
        linerContainerSearch.addView(viewContainerSearch)

        val searchButton = linerContainerSearch.findViewById<LinearLayout>(R.id.touch_listener)
        searchButton.setOnClickListener{
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.modal_window,null)
            val fromEditText = view.findViewById<EditText>(R.id.from)
            val whereEditText = view.findViewById<EditText>(R.id.where)

            val viewMenuSearch: View = layoutInflater.inflate(
                R.layout.menu_search,
                null
            )
            val menuSearch = view.findViewById<FrameLayout>(R.id.menu_search)
            menuSearch.addView(viewMenuSearch)

            val viewRecommendedBox: View = layoutInflater.inflate(
                R.layout.recommended_box,
                null
            )
            val recommendedBox = view.findViewById<FrameLayout>(R.id.recommended_box)
            recommendedBox.addView(viewRecommendedBox)

            dialog.setCancelable(true)
            dialog.setContentView(view)

            val bottomSheet = dialog.behavior
            bottomSheet.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED

            dialog.show()
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













        val retrofit = Retrofit.Builder()
            .baseUrl(" https://run.mocky.io/v3/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val ticketOfferApi = retrofit.create(TicketOfferApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val ticketOffer = ticketOfferApi.getTicketOffer()
            runOnUiThread {

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
//
                        linerAviaRecommendations.addView(viewAviaRecommendations)
                    }

            }
        }
    }
}
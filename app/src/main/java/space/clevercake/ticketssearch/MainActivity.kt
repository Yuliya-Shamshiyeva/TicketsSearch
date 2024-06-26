package space.clevercake.ticketssearch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.clevercake.ticketssearch.retrofit.OfferApi

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewContainerSearch: View = layoutInflater.inflate(
            R.layout.search_box,
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
        iconMainActivity.setImageResource(R.drawable.vector_blue)

        buttonHotels.setOnClickListener{
            startActivity(Intent(this@MainActivity, Hotels::class.java))
            finish()
        }
        buttonBrieflySpeaking.setOnClickListener{
            startActivity(Intent(this@MainActivity, BrieflySpeaking::class.java))
            finish()
        }
        buttonFollow.setOnClickListener{
            startActivity(Intent(this@MainActivity, Follow::class.java))
            finish()
        }
        buttonProfile.setOnClickListener{
            startActivity(Intent(this@MainActivity, Profile::class.java))
            finish()
        }













        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val offerApi = retrofit.create(OfferApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val offer = offerApi.getOffer()
            runOnUiThread{
                val linerContainerMusicFly: LinearLayout = findViewById(R.id.view_container_music_fly)
                linerContainerMusicFly.removeAllViews()
                for (offers in offer.offers) {
                    val viewContainerMusicFly: View = layoutInflater.inflate(
                        R.layout.music_box,
                        null
                    )
                    val image = viewContainerMusicFly.findViewById<ImageView>(R.id.image)
                    val title = viewContainerMusicFly.findViewById<TextView>(R.id.title)
                    val town = viewContainerMusicFly.findViewById<TextView>(R.id.town)
                    val price = viewContainerMusicFly.findViewById<TextView>(R.id.price)

                    if(offers.id == 1){
                        image.setImageResource(R.drawable.img_1)
                    }else if (offers.id == 2){
                        image.setImageResource(R.drawable.img_2)
                    }else{
                        image.setImageResource(R.drawable.img_3)
                    }

                    title.text = offers.title
                    town.text = offers.town
                    price.text = "от ${offers.price.value} ₽"

                    linerContainerMusicFly.addView(viewContainerMusicFly)
                }
            }
        }
    }
//    private fun showModalWindow(view: View){
//        val dialog = BottomSheetDialog(this)
//        val view = layoutInflater.inflate(R.layout.modal_window,null)
//        val fromEditText = view.findViewById<EditText>(R.id.from)
//        val whereEditText = view.findViewById<EditText>(R.id.where)
//        dialog.setCancelable(false)
//        dialog.setContentView(view)
//        dialog.show()
//    }
}
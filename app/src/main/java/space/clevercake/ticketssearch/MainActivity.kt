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
import space.clevercake.ticketssearch.retrofit.Offer

class MainActivity : AppCompatActivity() {
    private lateinit var offerViewModel: DataViewModel
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

            fromEditText.filters = arrayOf(CyrillicInputFilter())
            whereEditText.filters = arrayOf(CyrillicInputFilter())

            fromEditText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    checkFieldsAndStartSearch(fromEditText.text.trim().toString(), whereEditText.text.trim().toString(), dialog)
                }
            }

            whereEditText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    checkFieldsAndStartSearch(fromEditText.text.trim().toString(), whereEditText.text.trim().toString(), dialog)
                }
            }



            val viewMenuSearch: View = layoutInflater.inflate(
                R.layout.menu_search,
                null
            )
            val menuSearch = view.findViewById<FrameLayout>(R.id.menu_search)
            menuSearch.addView(viewMenuSearch)
            val buttonDifficultRoute = viewMenuSearch.findViewById<LinearLayout>(R.id.button_difficult_route)
            val buttonAnywhere = viewMenuSearch.findViewById<LinearLayout>(R.id.button_anywhere)
            val buttonWeekend = viewMenuSearch.findViewById<LinearLayout>(R.id.button_weekend)
            val buttonHotTickets = viewMenuSearch.findViewById<LinearLayout>(R.id.button_hot_tickets)
            buttonDifficultRoute.setOnClickListener{
                startActivity(Intent(this@MainActivity, DifficultRoute::class.java))
                finish()
                dialog.dismiss()
            }
            buttonAnywhere.setOnClickListener{

            }
            buttonWeekend.setOnClickListener{
                startActivity(Intent(this@MainActivity, Weekend::class.java))
                finish()
                dialog.dismiss()
            }
            buttonHotTickets.setOnClickListener{
                startActivity(Intent(this@MainActivity, HotTickets::class.java))
                finish()
                dialog.dismiss()
            }


            val viewRecommendedBox: View = layoutInflater.inflate(
                R.layout.recommended_box,
                null
            )
            val recommendedBox = view.findViewById<FrameLayout>(R.id.recommended_box)
            recommendedBox.addView(viewRecommendedBox)

            val buttonIstanbul = recommendedBox.findViewById<LinearLayout>(R.id.istanbul)
            val buttonSochi = recommendedBox.findViewById<LinearLayout>(R.id.sochi)
            val buttonPhuket = recommendedBox.findViewById<LinearLayout>(R.id.phuket)
            buttonIstanbul.setOnClickListener{

                val intent1 = Intent(this@MainActivity, SearchResults::class.java)
                intent1.putExtra("FROM", fromEditText.text.trim().toString())
                intent1.putExtra("WHERE", "Турция")

                startActivity(intent1)
                finish()
                dialog.dismiss()
            }
            buttonSochi.setOnClickListener{
                val intent1 = Intent(this@MainActivity, SearchResults::class.java)
                intent1.putExtra("FROM", fromEditText.text.trim().toString())
                intent1.putExtra("WHERE", "Сочи")

                startActivity(intent1)
                finish()
                dialog.dismiss()
            }
            buttonPhuket.setOnClickListener{
                val intent1 = Intent(this@MainActivity, SearchResults::class.java)
                intent1.putExtra("FROM", fromEditText.text.trim().toString())
                intent1.putExtra("WHERE", "Пхукет")

                startActivity(intent1)
                finish()
                dialog.dismiss()
            }




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



        offerViewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        offerViewModel.offerLiveData.observe(this, Observer { offer ->
            offer?.let { updateUI(it) }
        })

        offerViewModel.errorLiveData.observe(this, Observer { error ->
            showError(error)
        })
        offerViewModel.fetchOffers()
    }


    private fun updateUI(offer: Offer) {
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

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun checkFieldsAndStartSearch(fromText: String, whereText: String, dialog: BottomSheetDialog) {
        if (fromText.isNotBlank() && whereText.isNotBlank()) {
            val intent1 = Intent(this@MainActivity, SearchResults::class.java)
            intent1.putExtra("FROM", fromText)
            intent1.putExtra("WHERE", whereText)
            startActivity(intent1)
            finish()
            dialog.dismiss()
        } else {
            Toast.makeText(this@MainActivity, "Введите место отправления и место назначения", Toast.LENGTH_SHORT).show()
        }
    }
    private var back_pressed: Long = 0
    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity()
        } else Toast.makeText(
            baseContext, R.string.backsms,
            Toast.LENGTH_SHORT
        ).show()
        back_pressed = System.currentTimeMillis()
    }
}
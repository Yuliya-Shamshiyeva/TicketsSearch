package space.clevercake.ticketssearch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HotTickets : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)

        val text = findViewById<TextView>(R.id.text)
        text.text = "Горящие билеты"
        val buttonBack = findViewById<ImageView>(R.id.button_back)
        buttonBack.setOnClickListener{
            startActivity(Intent(this@HotTickets, MainActivity::class.java))
            finish()
        }

        val viewContainerMenuBottom: View = layoutInflater.inflate(
            R.layout.menu_bottom,
            null
        )
        val linerContainerMenuBottom: FrameLayout = findViewById(R.id.container_menu_bottom)
        linerContainerMenuBottom.addView(viewContainerMenuBottom)

        val buttonFollow = linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_follow)
        val buttonBrieflySpeaking =linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_briefly_speaking)
        val buttonHotels =linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_hotels)
        val buttonProfile =linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_profile)

        val iconMainActivity = linerContainerMenuBottom.findViewById<ImageView>(R.id.img_avia_tickets)
        iconMainActivity.setImageResource(R.drawable.icon_airplane2_blue)

        buttonFollow.setOnClickListener{
            startActivity(Intent(this@HotTickets, Follow::class.java))
            finish()
        }
        buttonBrieflySpeaking.setOnClickListener{
            startActivity(Intent(this@HotTickets, BrieflySpeaking::class.java))
            finish()
        }
        buttonHotels.setOnClickListener{
            startActivity(Intent(this@HotTickets, Hotels::class.java))
            finish()
        }
        buttonProfile.setOnClickListener{
            startActivity(Intent(this@HotTickets, Profile::class.java))
            finish()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@HotTickets, MainActivity::class.java))
        finish()
    }
}
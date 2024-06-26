package space.clevercake.ticketssearch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class Hotels : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotels)

        val viewContainerMenuBottom: View = layoutInflater.inflate(
            R.layout.menu_bottom,
            null
        )
        val linerContainerMenuBottom: FrameLayout = findViewById(R.id.container_menu_bottom)
        linerContainerMenuBottom.addView(viewContainerMenuBottom)

        val buttonMainActivity = linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_avia_tickets)
        val buttonBrieflySpeaking =linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_briefly_speaking)
        val buttonFollow =linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_follow)
        val buttonProfile =linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_profile)

        val iconHotels = linerContainerMenuBottom.findViewById<ImageView>(R.id.img_hotels)
        iconHotels.setImageResource(R.drawable.icon_bad_blue)

        buttonMainActivity.setOnClickListener{
            startActivity(Intent(this@Hotels, MainActivity::class.java))
            finish()
        }
        buttonBrieflySpeaking.setOnClickListener{
            startActivity(Intent(this@Hotels, BrieflySpeaking::class.java))
            finish()
        }
        buttonFollow.setOnClickListener{
            startActivity(Intent(this@Hotels, Follow::class.java))
            finish()
        }
        buttonProfile.setOnClickListener{
            startActivity(Intent(this@Hotels, Profile::class.java))
            finish()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@Hotels, MainActivity::class.java))
        finish()
    }
}
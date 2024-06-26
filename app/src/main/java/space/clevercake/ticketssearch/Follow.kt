package space.clevercake.ticketssearch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class Follow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow)

        val viewContainerMenuBottom: View = layoutInflater.inflate(
            R.layout.menu_bottom,
            null
        )
        val linerContainerMenuBottom: FrameLayout = findViewById(R.id.container_menu_bottom)
        linerContainerMenuBottom.addView(viewContainerMenuBottom)

        val buttonMainActivity = linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_avia_tickets)
        val buttonBrieflySpeaking =linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_briefly_speaking)
        val buttonHotels =linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_hotels)
        val buttonProfile =linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_profile)

        val iconFollow = linerContainerMenuBottom.findViewById<ImageView>(R.id.img_follow)
        iconFollow.setImageResource(R.drawable.icon_bell_blue)

        buttonMainActivity.setOnClickListener{
            startActivity(Intent(this@Follow, MainActivity::class.java))
            finish()
        }
        buttonBrieflySpeaking.setOnClickListener{
            startActivity(Intent(this@Follow, BrieflySpeaking::class.java))
            finish()
        }
        buttonHotels.setOnClickListener{
            startActivity(Intent(this@Follow, Hotels::class.java))
            finish()
        }
        buttonProfile.setOnClickListener{
            startActivity(Intent(this@Follow, Profile::class.java))
            finish()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@Follow, MainActivity::class.java))
        finish()
    }
}
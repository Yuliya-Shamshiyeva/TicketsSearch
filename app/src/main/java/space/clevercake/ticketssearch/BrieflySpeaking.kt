package space.clevercake.ticketssearch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class BrieflySpeaking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brieflyspeaking)

        val viewContainerMenuBottom: View = layoutInflater.inflate(
            R.layout.menu_bottom,
            null
        )
        val linerContainerMenuBottom: FrameLayout = findViewById(R.id.container_menu_bottom)
        linerContainerMenuBottom.addView(viewContainerMenuBottom)

        val buttonMainActivity = linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_avia_tickets)
        val buttonHotels = linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_hotels)
        val buttonFollow = linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_follow)
        val buttonProfile = linerContainerMenuBottom.findViewById<LinearLayout>(R.id.button_profile)

        val ivonBrieflySpeaking = linerContainerMenuBottom.findViewById<ImageView>(R.id.img_briefly_speaking)
        ivonBrieflySpeaking.setImageResource(R.drawable.icon_point_blue)

        buttonMainActivity.setOnClickListener{
            startActivity(Intent(this@BrieflySpeaking, MainActivity::class.java))
            finish()
        }
        buttonHotels.setOnClickListener{
            startActivity(Intent(this@BrieflySpeaking, Hotels::class.java))
            finish()
        }
        buttonFollow.setOnClickListener{
            startActivity(Intent(this@BrieflySpeaking, Follow::class.java))
            finish()
        }
        buttonProfile.setOnClickListener{
            startActivity(Intent(this@BrieflySpeaking, Profile::class.java))
            finish()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@BrieflySpeaking, MainActivity::class.java))
        finish()
    }
}
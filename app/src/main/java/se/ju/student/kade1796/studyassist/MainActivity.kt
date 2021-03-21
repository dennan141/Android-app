package se.ju.student.kade1796.studyassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //************THIS IS FOR TESTING; IT's CALLED TO LOG OUT USERS EVERY TIME *******
        //CAN SAFELY BE REMOVED
        //Authentication.instance.logOutUser()
        //CAN SAFELY BE REMOVED
        //************THIS IS FOR TESTING; IT's CALLED TO LOG OUT USERS EVERY TIME *******


        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_bar)
        val bottomNavigationController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_category, R.id.navigation_account, R.id.navigation_post
            )
        )

        setupActionBarWithNavController(bottomNavigationController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(bottomNavigationController)
    }


}
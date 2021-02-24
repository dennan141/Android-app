package se.ju.student.kade1796.studyassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categoryCampus          = findViewById<ImageButton>(R.id.category_campus)
        val categorySocialLife      = findViewById<ImageButton>(R.id.category_social_life)
        val categoryStudyHelp       = findViewById<ImageButton>(R.id.category_study_help)
        val categoryMath            = findViewById<ImageButton>(R.id.category_math)
        val categoryCourseMaterial  = findViewById<ImageButton>(R.id.category_course_material)
        val categoryITHelp          = findViewById<ImageButton>(R.id.category_it_help)
        val categoryExchangeStudies = findViewById<ImageButton>(R.id.category_exchange_studies)
        val categoryOther           = findViewById<ImageButton>(R.id.category_other)

        categoryCampus.setOnClickListener {

        }

        categorySocialLife.setOnClickListener {

        }

        categoryStudyHelp.setOnClickListener {

        }

        categoryMath.setOnClickListener {

        }

        categoryCourseMaterial.setOnClickListener {

        }

        categoryITHelp.setOnClickListener {

        }

        categoryExchangeStudies.setOnClickListener {

        }

        categoryOther.setOnClickListener {

        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_bar)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.page_home -> {
                    //Return to main activity
                    true
                }
                R.id.page_account -> {
                    //Open the account activity
                    true
                }
                R.id.page_post -> {
                    //Open the create view activity
                    true
                }
                else -> false
            }
        }
    }


}
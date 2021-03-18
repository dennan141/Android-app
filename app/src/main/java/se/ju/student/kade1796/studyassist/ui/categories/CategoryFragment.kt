package se.ju.student.kade1796.studyassist.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider


import se.ju.student.kade1796.studyassist.R

class CategoryFragment : Fragment() {

    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoryViewModel =
            ViewModelProvider(this).get(CategoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_category, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryCampus          = view.findViewById<ImageButton>(R.id.category_campus)
        val categorySocialLife      = view.findViewById<ImageButton>(R.id.category_social_life)
        val categoryStudyHelp       = view.findViewById<ImageButton>(R.id.category_study_help)
        val categoryMath            = view.findViewById<ImageButton>(R.id.category_math)
        val categoryCourseMaterial  = view.findViewById<ImageButton>(R.id.category_course_material)
        val categoryITHelp          = view.findViewById<ImageButton>(R.id.category_it_help)
        val categoryExchangeStudies = view.findViewById<ImageButton>(R.id.category_exchange_studies)
        val categoryOther           = view.findViewById<ImageButton>(R.id.category_other)

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
    }
}
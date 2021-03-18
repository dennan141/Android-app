package se.ju.student.kade1796.studyassist.ui.categories

import androidx.lifecycle.ViewModel
import se.ju.student.kade1796.studyassist.Category
import se.ju.student.kade1796.studyassist.R

class CategoryViewModel : ViewModel() {

    fun tempCategoryList(): ArrayList<Category> {
        val list = mutableListOf(
            Category("Campus", "campus", R.drawable.image_campus),
            Category("Social Life", "social", R.drawable.image_social),
            Category("Study Help", "study", R.drawable.image_study),
            Category("IT Help", "it", R.drawable.image_it_help),
            Category("Math", "math", R.drawable.image_math),
            Category("Course Material", "study", R.drawable.image_course_material),
            Category("Exchange Studies", "study", R.drawable.image_exchange),
            Category("Other", "study", R.drawable.image_other)
        )
        return list as ArrayList<Category>
    }
}


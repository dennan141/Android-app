package se.ju.student.kade1796.studyassist.ui.categories

import androidx.lifecycle.ViewModel
import se.ju.student.kade1796.studyassist.Categories
import se.ju.student.kade1796.studyassist.R

class CategoryViewModel : ViewModel() {

    fun tempCategoryList(): ArrayList<Categories> {
        val list = mutableListOf(
            Categories("Campus", "campus", R.drawable.image_campus),
            Categories("Social Life", "social", R.drawable.image_social),
            Categories("Study Help", "study", R.drawable.image_study),
            Categories("IT Help", "it", R.drawable.image_it_help),
            Categories("Math", "math", R.drawable.image_math),
            Categories("Course Material", "study", R.drawable.image_course_material),
            Categories("Exchange Studies", "study", R.drawable.image_exchange),
            Categories("Other", "study", R.drawable.image_other)
        )
        return list as ArrayList<Categories>
    }
}


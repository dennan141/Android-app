package se.ju.student.kade1796.studyassist.ui.categories

import androidx.lifecycle.ViewModel
import se.ju.student.kade1796.studyassist.Categories
import se.ju.student.kade1796.studyassist.R

class CategoryViewModel : ViewModel() {

    fun getCategoryList(): ArrayList<Categories> {

        var list = mutableListOf(
            Categories("Campus", 0, R.drawable.image_campus),
            Categories("Social Life",  0, R.drawable.image_social),
            Categories("Study Help", 0, R.drawable.image_study),
            Categories("IT Help", 0, R.drawable.image_it_help),
            Categories("Math", 0, R.drawable.image_math),
            Categories("Course Material", 0, R.drawable.image_course_material),
            Categories("Exchange Students", 0, R.drawable.image_exchange),
            Categories("Other", 0, R.drawable.image_other)
        )
        list = setCategoryListValues(list as ArrayList<Categories>)
        return list
    }
}

private fun setCategoryListValues(list: ArrayList<Categories>): ArrayList<Categories> {
    for (i in list) {
        i.categoryTitleStringResource = when (i.categoryTitle) {
            "Campus" -> R.string.text_campus
            "Social Life" -> R.string.text_social_life
            "Study Help" -> R.string.text_study_help
            "IT Help" -> R.string.text_it_help
            "Math" -> R.string.text_math
            "Course Material" -> R.string.text_course_material
            "Exchange Students" -> R.string.text_exchange_students
            "Other" -> R.string.text_other
            else -> R.string.text_other
        }
    }
    return list
}


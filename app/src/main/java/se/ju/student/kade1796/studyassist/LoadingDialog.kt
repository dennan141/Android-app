package se.ju.student.kade1796.studyassist

import android.app.Activity
import android.app.AlertDialog
import androidx.fragment.app.Fragment
import se.ju.student.kade1796.studyassist.ui.categories.CategoryFragment

class LoadingDialog(
    private var fragment: Fragment

) {
    private lateinit var alertDialog: AlertDialog

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(fragment.context)
        val inflater = fragment!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_loading_dialog, null))
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog.show()
    }

    fun dismissDialog(){
        alertDialog.dismiss()
    }

}
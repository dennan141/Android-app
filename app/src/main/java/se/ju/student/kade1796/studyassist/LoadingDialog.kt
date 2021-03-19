package se.ju.student.kade1796.studyassist

import android.app.Activity
import android.app.AlertDialog
import androidx.fragment.app.Fragment
import se.ju.student.kade1796.studyassist.ui.categories.CategoryFragment

class LoadingDialog{
    private var activity = Activity()
    private var fragment = Fragment()
    constructor(activity: Activity){
        this.activity = activity
    }
    constructor(fragment: Fragment){
        this.fragment = fragment
    }
    private lateinit var alertDialog: AlertDialog

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(fragment.context)
        val inflater = fragment!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_loading_dialog, null))
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog.show()
    }

    fun startLoadingDialogActivity() {
        val builder = AlertDialog.Builder(activity)
        val inflater = this.activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_loading_dialog, null))
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog.show()
    }

    fun dismissDialog(){
        alertDialog.dismiss()
    }

}
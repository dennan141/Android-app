package se.ju.student.kade1796.studyassist.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccountViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is account Fragment"
    }
    val text: LiveData<String> = _text
}
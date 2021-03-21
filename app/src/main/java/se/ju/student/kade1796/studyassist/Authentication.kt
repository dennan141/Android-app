package se.ju.student.kade1796.studyassist

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Authentication {

    companion object{
        val instance = Authentication()
    }

    private var firebaseAuth = FirebaseAuth.getInstance()
    var auth = Firebase.auth

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }




}
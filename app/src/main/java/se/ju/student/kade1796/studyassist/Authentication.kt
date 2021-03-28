package se.ju.student.kade1796.studyassist

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Authentication {

    companion object {
        val instance = Authentication()
    }

    private var firebaseAuth = FirebaseAuth.getInstance()
    var auth = Firebase.auth

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun logOutUser() {
        auth.signOut()
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }


    fun isAuthorized(userId: String): Boolean {
        return auth.currentUser?.uid == userId
    }


}
package se.ju.student.kade1796.studyassist.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import se.ju.student.kade1796.studyassist.*

class AccountFragment : Fragment() {
    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        accountViewModel =
            ViewModelProvider(this).get(AccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_account, container, false)

        val accountEmail: TextView = root.findViewById(R.id.account_email)
        accountViewModel.text.observe(viewLifecycleOwner, Observer {
            if (!Authentication.instance.isLoggedIn()) {
                accountEmail.text = "Not logged in"
            } else {
                accountEmail.text = Authentication.instance.getCurrentUser()?.email.toString()
            }

        })




        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val logOutButton = view.findViewById<Button>(R.id.log_out_button)
        val signUpButton = view.findViewById<Button>(R.id.sign_up_button)
        val listView = view.findViewById<ListView>(R.id.account_listView)
        val listOfThreads = Repository.instance.userThreads as ArrayList




        //Sets text on log in/out button
        if (Authentication.instance.isLoggedIn()) {
            logOutButton.text = "Log out"
            signUpButton.visibility = View.INVISIBLE
        } else {
            signUpButton.visibility = View.VISIBLE
            logOutButton.text = "Log in"
        }




        logOutButton.setOnClickListener {
            if (Authentication.instance.isLoggedIn()) {
                Authentication.instance.logOutUser()
                val intent = Intent(this.context, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this.context, LogInActivity::class.java)
                startActivity(intent)
            }
        }


        signUpButton.setOnClickListener {
            val intent = Intent(this.context, SignUpActivity::class.java)
            startActivity(intent)
        }


    }


}
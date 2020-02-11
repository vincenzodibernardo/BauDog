package com.example.baudog


import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.navigation.Navigation
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_registration.*


class HomeFragment : Fragment()
{
    private var GoogleSignInClient : GoogleSignInClient ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        Bottone_Ritrovamento.setOnClickListener {

            val currentUser = FirebaseAuth.getInstance().currentUser

            if (currentUser != null)
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_trovatoFragment)
            else {
                var arrivo = "Trovato"
                Navigation.findNavController(view)
                    .navigate(R.id.action_homeFragment_to_registration)
            }

        }

        Bottone_ListRitrovamento.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_ListTrovatoFragment) }

        Bottone_ListSmarrimento.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_listSmarritiFragment) }


    }


    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    override fun onPause() {
        super.onPause()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?)
    {
        Log.d("MENUOPT","ENTRO IN OnCreateOptionMenu")
        inflater!!.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }


    private fun logout()
    {
        //LOGOUT SIMPLE
        FirebaseAuth.getInstance().signOut()


        //LOGOUT GOOGLE
        GoogleSignInClient?.signOut()

        //LOGOUT FACEBOOK
        LoginManager.getInstance().logOut()


        Toast.makeText(context, "LOGOUT", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {


        //get item id to handle item clicks
        val id = item!!.itemId

        //handle item clicks
        if (id == R.id.Logout_Item)
        {
            logout()
            Navigation.findNavController(view!!).navigateUp()

        }
        if (id == R.id.Profilo_Item)
        {

            Toast.makeText(context, "PROFILO", Toast.LENGTH_SHORT).show()

        }

        if (id == R.id.Login_Item)
        {

            Toast.makeText(context, "LOGIN ", Toast.LENGTH_SHORT).show()
            textView_Login.visibility = View.VISIBLE
            textView_SignIn.visibility=View.GONE

            button_LoginUserPassword.visibility=View.VISIBLE
            button_LoginFacebook.visibility=View.VISIBLE
            button_LoginGoogle.visibility=View.VISIBLE

            button_Google.visibility=View.GONE
            button_Facebook.visibility=View.GONE
            button_submit.visibility=View.GONE

            editText_Username.error = null
            editText_Password.error = null
        }

        if (id == R.id.SignIn_Item)
        {

            Toast.makeText(context, "LOGIN ", Toast.LENGTH_SHORT).show()
            textView_Login.visibility = View.GONE
            textView_SignIn.visibility=View.VISIBLE

            button_LoginUserPassword.visibility=View.GONE
            button_LoginFacebook.visibility=View.GONE
            button_LoginGoogle.visibility=View.GONE

            button_Google.visibility=View.VISIBLE
            button_Facebook.visibility=View.VISIBLE
            button_submit.visibility=View.VISIBLE

            editText_Username.error = null
            editText_Password.error = null
        }
        return true




    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser!=null)
        {
            menu!!.findItem(R.id.Login_Item).isVisible=false
            menu.findItem(R.id.SignIn_Item).isVisible=false
            menu.findItem(R.id.Profilo_Item).isVisible=true
            menu.findItem(R.id.Logout_Item).isVisible=true


        }

        else
        {
            menu!!.findItem(R.id.Login_Item).isVisible=true
            menu.findItem(R.id.SignIn_Item).isVisible=true
            menu.findItem(R.id.Profilo_Item).isVisible=false
            menu.findItem(R.id.Logout_Item).isVisible=false
        }


    }







}


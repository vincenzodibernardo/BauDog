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

        Bottone_Smarrimento.setOnClickListener {
            Passaggio("smarrito")
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_trovatoFragment)

        }


        Bottone_Ritrovamento.setOnClickListener {

            val currentUser = FirebaseAuth.getInstance().currentUser

            if (currentUser!=null)
            {   Passaggio("trovato")
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_trovatoFragment) }

            else
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_registration)
        }

        Bottone_ListRitrovamento.setOnClickListener {
            Passaggio("trovati")
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_ListTrovatoFragment) }

        Bottone_ListSmarrimento.setOnClickListener {
            Passaggio("smarriti")
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_ListTrovatoFragment) }
    }


    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }

    override fun onPause() {
        super.onPause()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR


    }







}


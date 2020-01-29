package com.example.baudog


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Bottone_Smarrimento.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_smarritoFragment) }

        Bottone_Ritrovamento.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser!=null)
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_trovatoFragment)
            else
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_registration)
        }
    }





}

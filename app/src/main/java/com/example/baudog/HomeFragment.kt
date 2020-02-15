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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)
    }
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
            if (logged=="Y")
            {
                Passaggio("smarriti")
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_trovatoFragment)
            }
            else
                Passaggio("smarriti")
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_registration)

        }


        Bottone_Ritrovamento.setOnClickListener {

            if (Logged("LOGGED", "", "", "", "", "", "", "", "", "") == "Y")
            {
                Passaggio("trovati")
                Navigation.findNavController(view)
                    .navigate(R.id.action_homeFragment_to_trovatoFragment)
            }

            else {
                Passaggio("trovati")
                Navigation.findNavController(view)
                    .navigate(R.id.action_homeFragment_to_registration)
            }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?)
    {
        Log.d("MENUOPT","ENTRO IN OnCreateOptionMenu")
        inflater!!.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {


        //get item id to handle item clicks
        val id = item!!.itemId
        //handle item clicks



        if (id == R.id.Login_Item)
        {
            //do your action here, im just showing toast
            Toast.makeText(context, "LOGIN", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view!!).navigate(R.id.action_homeFragment_to_registration)


        }

        if (id == R.id.SignIn_Item)
        {
            //do your action here, im just showing toast
            Navigation.findNavController(view!!).navigate(R.id.action_homeFragment_to_creaUserFragment)


        }

        if (id == R.id.Profilo_Item)
        {
            //do your action here, im just showing toast
            Navigation.findNavController(view!!).navigate(R.id.action_homeFragment_to_profiloFragment)


        }

        if (id == R.id.Logout_Item)
        {
            Logged("LOGOUT","","","","","","","","","")

        }




        return super.onOptionsItemSelected(item)
    }



    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)



        if (logged=="Y")
        {
            menu!!.findItem(R.id.Login_Item).isVisible = false
            menu.findItem(R.id.SignIn_Item).isVisible = false
            menu.findItem(R.id.Profilo_Item).isVisible = true
            menu.findItem(R.id.Logout_Item).isVisible = true
            menu.findItem(R.id.Impostazioni_Item).isVisible = true
            menu.findItem(R.id.Home_Item).isVisible = false
        }

        else
        {
            menu!!.findItem(R.id.Login_Item).isVisible = true
            menu.findItem(R.id.SignIn_Item).isVisible = true
            menu.findItem(R.id.Profilo_Item).isVisible = false
            menu.findItem(R.id.Logout_Item).isVisible = false

        }

        menu.findItem(R.id.Impostazioni_Item).isVisible = true
        menu.findItem(R.id.Home_Item).isVisible = false








    }






}


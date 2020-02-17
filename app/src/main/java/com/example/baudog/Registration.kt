package com.example.baudog

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_registration.*


class Registration : Fragment() {
    private val PREF_NAME = "BauDog"
    private val PREF_USERNAME = "Username"
    private val PREF_PASSWORD = "Password"
    private lateinit var sharedPref: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)




    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = activity!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        leggiParametri()









        //USER & PASSWORD LOGIN/SIGNIN

        button_LoginUserPassword.setOnClickListener {

            if(Control())
            {
                Login(editText_Username.text.toString(), editText_Password.text.toString())


                    val editor : SharedPreferences.Editor= sharedPref.edit()

                    val username = editText_Username.text.toString()
                    editor.putString(PREF_USERNAME, username)

                    val password = editText_Password.text.toString()
                    editor.putString(PREF_PASSWORD, password)

                    editor.apply()    // Salva le modifiche

            }

        }





        textView_Registrati.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_registration_to_creaUserFragment) }


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

        if (id == R.id.SignIn_Item)
        {
            Navigation.findNavController(view!!).navigate(R.id.action_registration_to_creaUserFragment)

        }

        if (id == R.id.Home_Item)
        {
            Navigation.findNavController(view!!).navigate(R.id.action_registration_to_homeFragment)
        }
        //AGGIUNGERE IMPOSTAZIONI
        return true




    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)


            menu!!.findItem(R.id.Login_Item).isVisible=false
            menu.findItem(R.id.SignIn_Item).isVisible=true
            menu.findItem(R.id.Profilo_Item).isVisible=false
            menu.findItem(R.id.Logout_Item).isVisible=false
            menu.findItem(R.id.Home_Item).isVisible=true


        //AGGIUNGERE IMPOSTAZIONI



    }

    private fun Control():Boolean
    {
        var errore  = false

        if (editText_Username.text.toString().trim().isEmpty()) {
            editText_Username.error = "Riempire il campo \'Username\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore = true
        }

        if (editText_Password.text.toString().trim().isEmpty()) {
            editText_Password.error = "Riempire il campo \'Password\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore = true
        }

        if (errore)
        {
            return false
        }

        return true

    }

    private fun Login(username : String, password :String)
    {
        val ref = FirebaseDatabase.getInstance().getReference("User/")

        ref.addValueEventListener(object : ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


            override fun onDataChange(p0: DataSnapshot) {
                if (view != null) {
                    if (p0.exists()) {
                        for (h in p0.children)
                        {
                            val user: User? = h.getValue(User::class.java)
                            if (username == user?.username) {
                                Log.d("USER", "USER TROVATO: $username")
                                if (user.password == password) {
                                    Log.d("USER", "PASSWORD TROVATA TROVATO : $password")
                                    Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show()
                                    Logged(
                                        "LOGIN",
                                        user.nome,
                                        user.cognome,
                                        user.sesso,
                                        user.eta,
                                        user.email,
                                        user.username,
                                        user.cellulare,
                                        user.ID,
                                        user.uriImage,
                                        user.password
                                    )
                                    if (Logged(
                                            "LOGGED",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            ""
                                        ) == "Y"
                                    )
                                    {

                                        if (Passaggio("QUI") == "smarriti" || Passaggio("QUI") == "trovati")
                                            Navigation.findNavController(view!!).navigate(R.id.action_registration_to_trovatoFragment)
                                        else
                                            Navigation.findNavController(view!!).navigate(R.id.action_registration_to_profiloFragment)
                                    }


                                    break

                                } else
                                    Toast.makeText(
                                        activity,
                                        "Password Incorrect",
                                        Toast.LENGTH_SHORT
                                    ).show()
                            } else
                                Toast.makeText(
                                    activity,
                                    "User not registred",
                                    Toast.LENGTH_SHORT
                                ).show()

                        }


                    }

                }
            }

        })


    }

    private fun leggiParametri()
    {
        val username = sharedPref.getString(PREF_USERNAME, "")
        editText_Username.setText(username)

        val password = sharedPref.getString(PREF_PASSWORD, "")
        editText_Password.setText(password)
    }
















}

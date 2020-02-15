package com.example.baudog

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.navigation.Navigation
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_registration.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class Registration : Fragment() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)




    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }


    fun printHashKey(pContext: Context) {
        try {
            var info = pContext.packageManager
                .getPackageInfo(pContext.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.d("DIBERNARDO1", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException)
        {
            Log.d("DIBERNARDO1", "ERRORE 1")
        }

        catch (e: Exception)
        {
            Log.d("DIBERNARDO1", "ERRORE 2")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView_Registrati.setOnClickListener {

            textView_Login.text = "SIGN IN"

            button_LoginUserPassword.visibility=View.GONE

            editText_Username.error = null
            editText_Password.error = null


        }





        //USER & PASSWORD LOGIN/SIGNIN

        button_LoginUserPassword.setOnClickListener {

            if(Control())
                Login(editText_Username.text.toString(),editText_Password.text.toString())

        }



        printHashKey(context!!)


        textView_Registrati.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_registration_to_creaUserFragment) }


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

                    if (p0.exists())
                    {
                        for (h in p0.children)
                        {
                            val user: User? = h.getValue(User::class.java)
                                if (username == user?.username)
                                {
                                    if (user.password == password)
                                    {
                                        Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show()
                                        Logged("LOGIN",user.nome,user.cognome,user.sesso,user.eta,user.email,user.username, user.cellulare,user.ID,user.uriImage)

                                        if(Passaggio("QUI")=="smarriti"||Passaggio("QUI")=="trovati")
                                            Navigation.findNavController(view!!).navigate(R.id.action_registration_to_trovatoFragment)
                                        break

                                    } else
                                        Toast.makeText(activity, "Password Incorrect", Toast.LENGTH_SHORT).show()
                                }
                                else
                                    Toast.makeText(activity, "User not registred", Toast.LENGTH_SHORT).show()

                        }


                    }

                }
            })

    }













}

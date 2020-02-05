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
import kotlinx.android.synthetic.main.fragment_registration.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class Registration : Fragment() {


    private var GoogleSignInClient : GoogleSignInClient ? = null

    var RC_SIGN_IN = 1000
    var callbackManager = CallbackManager.Factory.create()


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


        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        GoogleSignInClient = GoogleSignIn.getClient(context!!,gso)


        //USER & PASSWORD LOGIN/SIGNIN
        button_submit.setOnClickListener {
            if(Control())
                createUser()
        }
        button_LoginUserPassword.setOnClickListener {
            if(Control())
                loginEmail()
        }


        //FACEBOOK LOGIN/SIGNIN
        button_Google.setOnClickListener {

            val signInIntent = GoogleSignInClient?.signInIntent
            startActivityForResult(signInIntent,RC_SIGN_IN)
        }
        button_LoginGoogle.setOnClickListener {

            val signInIntent = GoogleSignInClient?.signInIntent
            startActivityForResult(signInIntent,RC_SIGN_IN) }

        //FACEBOOK LOGIN/SIGNIN
        button_Facebook.setOnClickListener { FacebookLogin() }
        button_LoginFacebook.setOnClickListener { FacebookLogin() }


        printHashKey(context!!)
    }




    private fun createUser()
    {
        var email = editText_Username.text.toString()
        var password = editText_Password.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful)
                Toast.makeText(context,"Utente Registrato",Toast.LENGTH_LONG).show()

        }

    }


    private fun loginEmail()
    {
        var email = editText_Username.text.toString()
        var password = editText_Password.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful)
            {
                Toast.makeText(context,"LOGIN EFFETTUATO",Toast.LENGTH_LONG).show()
                Navigation.findNavController(view!!).navigate(R.id.action_registration_to_trovatoFragment)

            }

            if (task.isCanceled)
            {
                Toast.makeText(context,"ERRORE: RIPROVA TRA POCO",Toast.LENGTH_LONG).show()
            }
        }

    }


    private fun FirebaseAuthWithGoogle(acct:GoogleSignInAccount)
    {
        val credential = GoogleAuthProvider.getCredential(acct.idToken,null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                Toast.makeText(context!!, "Utente Registrato", Toast.LENGTH_LONG).show()
                Navigation.findNavController(view!!).navigate(R.id.action_registration_to_trovatoFragment)
            }

        }
    }


    private fun FacebookLogin()
    {
        LoginManager.getInstance().loginBehavior = LoginBehavior.WEB_VIEW_ONLY
        LoginManager.getInstance().logInWithReadPermissions(this,Arrays.asList("email","public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?)
            {
                FirebaseAuthWithFacebook(result)
                Log.d("DIBERNARDO1", "OK1")
            }

            override fun onCancel() {
                Log.d("DIBERNARDO1", "ERRORE 3")

            }

            override fun onError(error: FacebookException?)
            {
                Log.d("DIBERNARDO1", "ERRORE 4")
            }


        })
    }

    private fun FirebaseAuthWithFacebook(result: LoginResult?)
    {
        val credential = FacebookAuthProvider.getCredential(result?.accessToken?.token!!)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful)
            {
                Toast.makeText(context!!,"Utente Registrato",Toast.LENGTH_LONG).show()
                Navigation.findNavController(view!!).navigate(R.id.action_registration_to_trovatoFragment)
            }

            if (task.isCanceled)
                Toast.makeText(context!!,"Utente NON Registrato",Toast.LENGTH_LONG).show()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)

         Log.d("DIBERNARDO1", "OK FORSE")

        if (requestCode==RC_SIGN_IN)
        {
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            var account= task.getResult(ApiException::class.java)
            FirebaseAuthWithGoogle(account!!)

            Log.d("DIBERNARDO1", "ERRORE 6")
        }

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

    private fun Control():Boolean
    {
        var errore : Boolean = false

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








}

package com.example.baudog

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_crea_user.*
import java.util.*


class CreaUserFragment : Fragment() {


    var sesso : String = "Maschio"
    var uriImmagineProfilo : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crea_user, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //RADIOBUTTON MASCHIO E FEMMINA
        radioButton_Femmina.setOnClickListener {

            sesso = "Femmina"
        }
        radioButton_Maschio.setOnClickListener {

            sesso = "Maschio"
        }


        //AGGIUNGO IMMAGINE DI PROFILO
        circleImageView_Profilo.setOnClickListener {

            galleriaImmagineProfilo()
        }



        //BOTTONI CREA USER E ANNULLA
        button_CreaUser.setOnClickListener {

           if (VerificaCampi())
           {
               uploadImmagineProfilo()
               Navigation.findNavController(view).navigate(R.id.action_creaUserFragment_to_registration)
           }

        }
        button_AnnullaUser.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_creaUserFragment_to_registration) }
    }




    private fun galleriaImmagineProfilo()
    {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK)
        {


            uriImmagineProfilo = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, uriImmagineProfilo)

            Picasso.get().load(uriImmagineProfilo).into(circleImageView_Profilo)


        }
    }

    private fun uploadImmagineProfilo() {

        if (uriImmagineProfilo==null)
        {   Registrazione("") }

        else
        {
            val filename= UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/images/user/$filename")
            ref.putFile(uriImmagineProfilo!!).addOnSuccessListener {

                ref.downloadUrl.addOnSuccessListener {

                    Registrazione(it.toString())
                }

            }



        }
    }


    private fun Registrazione(ImmagineProfiloUrl : String?)
    {

        val db_NomeUtente       : String        = editText_NomeCrea.text.toString()

        val db_Cognome_Utente   : String        = editText_CognomeCrea.text.toString()

        val db_Eta_Utente       : String        = editText_EtaCrea.text .toString()

        val db_Sesso_Utente     : String        = sesso

        val db_Email_Utente     : String        = editText_EmailCrea.text.toString()

        val db_Numero_Utente    : String        = editText_CellulareCrea.text.toString()

        val db_Password_Utente  : String        = editText_PasswordCrea.text.toString()

        val db_ImageUrl_Utente  : String        = ImmagineProfiloUrl.toString()

        val db_Username         : String        = editText_UsernameCrea.text.toString()



        val ref = FirebaseDatabase.getInstance().getReference("User/")

        val db_ID_Utente: String = ref.push().key.toString()


        val user = User(db_NomeUtente,db_Cognome_Utente,db_Eta_Utente,db_Email_Utente,db_Sesso_Utente,db_Numero_Utente, db_Password_Utente, db_Username,db_ID_Utente,db_ImageUrl_Utente)




        ref.child(db_ID_Utente).setValue(user)
            .addOnCompleteListener {

                Toast.makeText(activity, "UTENTE REGISTRATO CON SUCCESSO", Toast.LENGTH_SHORT).show()
            }

    }



    private fun VerificaCampi () : Boolean
    {
        var errore = false

        if (editText_NomeCrea.text.toString().trim().isEmpty()) {
            editText_NomeCrea.error = "Riempire il campo \'Nome\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore = true
        }


        if (editText_CognomeCrea.text.toString().trim().isEmpty()) {
            editText_NomeCrea.error = "Riempire il campo \'Cognome\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore = true
        }


        if (editText_EtaCrea.text.toString().trim().isEmpty()) {
            editText_EtaCrea.error = "Riempire il campo \'Età\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore = true
        }


        if (editText_CellulareCrea.text.toString().trim().isEmpty()) {
            editText_CellulareCrea.error = "Riempire il campo \'Cellulare\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore = true
        }

        if (editText_EmailCrea.text.toString().trim().isEmpty()) {
            editText_EmailCrea.error = "Riempire il campo \'Email\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore = true
        }

        if (editText_PasswordCrea.text.toString().trim().isEmpty()) {
            editText_PasswordCrea.error = "Riempire il campo \'Password\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore = true
        }

        if (editText_RipPaswordCrea.text.toString().trim().isEmpty()) {
            editText_RipPaswordCrea.error = "Riempire il campo \'Password\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore = true

        }

        if (editText_UsernameCrea.text.toString().trim().isEmpty()) {
            editText_UsernameCrea.error = "Riempire il campo \'Username\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore = true

        }

        if (editText_RipPaswordCrea.text.toString()!=editText_PasswordCrea.text.toString())
        {
            editText_RipPaswordCrea.error   =   "Le Password non coincidono"
            editText_PasswordCrea.error     =   "Le Password non coincidono"
            errore =    true

        }


            if (errore) { return false }

        return true


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
            Navigation.findNavController(view!!).navigate(R.id.action_creaUserFragment_to_registration)


        }


        if (id == R.id.Home_Item)
        {
            //do your action here, im just showing toast
            Toast.makeText(context, "LOGIN", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view!!).navigate(R.id.action_creaUserFragment_to_homeFragment)


        }



        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)




            menu!!.findItem(R.id.Login_Item).isVisible=true
            menu.findItem(R.id.SignIn_Item).isVisible=false
            menu.findItem(R.id.Profilo_Item).isVisible=false
            menu.findItem(R.id.Logout_Item).isVisible=false
            menu.findItem(R.id.Home_Item).isVisible=true








    }
}


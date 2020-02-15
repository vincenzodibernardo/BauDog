package com.example.baudog

import android.R.layout.simple_list_item_1
import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.Navigation
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_trovato.*
import java.util.*

class TrovatoFragment : Fragment() {

    private var chipValue: Boolean = true                               // IL CANE HA IL CHIP ?
    private var collareValue: Boolean = true                            // IL CANE HA IL COLLARE ?
    private var sesso: String = "Maschio"                               // M O F ?



    private var firebaseStorage: FirebaseStorage? = null                //VARIABILE PER FIREBASE
    private var storageReference: StorageReference? = null              //VARIABILE PER FIREBASE
    private var selectedPhotoUri : Uri?=null                            //VARIABILE PER FIREBASE

    private var smarr_trov : String = Passaggio("QUI")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)                                    //MENU 3 PALLINE
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trovato, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Serve a far mostrare la lista delle razze dei cani in Multiline text
        val res: Resources = resources
        val Razze = res.getStringArray(R.array.razze)
        val adapter = ArrayAdapter(context!!, simple_list_item_1, Razze)


        //SELEZIONA RAZZA CANE : LISTA
        Multi_SelezionaRazza.threshold = 0
        Multi_SelezionaRazza.setAdapter(adapter)
        Multi_SelezionaRazza.setOnClickListener { Multi_SelezionaRazza.showDropDown() }


        //BOTTONI COLLARE SI E NO
        radioButton_CollareSi.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                collareValue = true
                editText_ColoreCollare.text = null
                editText_NomeCollare.text = null
                editText_NomeCollare.visibility = View.VISIBLE
                editText_ColoreCollare.visibility = View.VISIBLE

                Log.d("Debug 1", "Collare : Hai premuto SI")
            }


        }
        radioButton_CollareNo.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                editText_ColoreCollare.text = null
                editText_NomeCollare.text = null
                editText_ColoreCollare.text.insert(0, "NULL")
                editText_NomeCollare.text.insert(0, "NULL")
                editText_ColoreCollare.visibility = View.GONE
                editText_NomeCollare.visibility = View.GONE
                collareValue = false
                Log.d("Debug 1", "Collare : Hai premuto No")
            }

        }



        //BOTTONI SALVA E ANNULLA
        button_AnnullaUser.setOnClickListener {
            Log.d("Debug 1", "ANNULLA: Hai premuto Annulla")

            Toast.makeText(context,"CANE NON SALVATO", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).navigateUp()
        }
        button_SalvaCane.setOnClickListener {
            Log.d("Debug 1", "SALVA: Hai premuto SALVA")
            if (campiValidi())
            {

                uploadImage()
            }
        }



        //BOTTONI CHIP SI E NO
        radioButton_ChipSi.setOnClickListener {
            Log.d("Debug 1", " CHIP : Hai premuto SI")
            chipValue = true
        }
        radioButton_ChipNo.setOnClickListener {
            Log.d("Debug 1", " CHIP : Hai premuto NO")
            chipValue = false
        }


        //BOTTONI SESSO MASCHIO E FEMMINA
        radioButton_Femmina.setOnClickListener {
            Log.d("Debug 1", " SESSO : Hai premuto Femmina")
            sesso = "Femmina"
        }
        radioButton_Maschio.setOnClickListener {
            Log.d("Debug 1", " SESSO : Hai premuto Maschio")
            sesso = "Maschio"
        }


        //CARICA FOTO DA GALLERIA
        Bottone_CaricaFoto.setOnClickListener {
            Log.d("Debug 1", " FOTO : Hai premuto Galleria Immagine")
            galleriaImmagine()
        }

        //EDIT TROVATO/SMARRITO
        if (smarr_trov=="trovati"){textView_Trovato.text="TROVATO"}
        if (smarr_trov=="smarriti"){textView_Trovato.text="SMARRITO"}


        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference


    }

    private fun galleriaImmagine() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0)


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if(requestCode==0 && resultCode==Activity.RESULT_OK )
        {


            selectedPhotoUri=data.data
            val bitmap=MediaStore.Images.Media.getBitmap(activity!!.contentResolver,selectedPhotoUri)

            ImmagineCane.setImageBitmap(bitmap)
            ImmagineCane.visibility=View.VISIBLE


        }



    }


    private fun uploadImage() {

        if (selectedPhotoUri==null)
        {   salvaCane("") }

        else
        {
            val filename=UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/images/cani/$filename")
            ref.putFile(selectedPhotoUri!!).addOnSuccessListener {

                ref.downloadUrl.addOnSuccessListener {

                    salvaCane(it.toString())
                }

            }



        }
    }


    private fun campiValidi(): Boolean {

        var errore = false

        if (Multi_SelezionaRazza.text.toString().trim().isEmpty()) {
            Multi_SelezionaRazza.error = "Riempire il campo \'Razza\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore = true
        }


        if (editText_Colore.text.toString().trim().isEmpty()) {
            editText_Colore.error = "Riempire il campo \'Colore\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore = true
        }

        if (editText_ColoreCollare.text.toString().trim().isEmpty()) {
            editText_ColoreCollare.error = "Riempire il campo \'Colore Collare\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore = true
        }

        if (errore) {
            return false
        }

        return true

    }


    private fun salvaCane(profileImageUrl:String?) {

        val db_Razza: String = Multi_SelezionaRazza.text.toString()

        val db_Colore: String = editText_Colore.text.toString()

        val db_Sesso: String = sesso

        val db_Chip: Boolean = chipValue

        val db_Collare: Boolean = collareValue

        val db_ColoreCollare: String = editText_ColoreCollare.text.toString()

        val db_NomeCollare: String = editText_NomeCollare.text.toString()

        val db_Info: String = editText_InfoAggiuntive.text.toString()

        val db_ProfileImageUrl:String?=profileImageUrl.toString()

        val ref = FirebaseDatabase.getInstance().getReference("cani/$smarr_trov")

        val db_caneID: String = ref.push().key.toString()


        val cane = Cane(
            db_caneID,
            db_Razza,
            db_Sesso,
            db_Colore,
            db_Chip,
            db_Collare,
            db_ColoreCollare,
            db_NomeCollare,
            db_Info,
            db_ProfileImageUrl

        )


        ref.child(db_caneID).setValue(cane)
            .addOnCompleteListener {

                Toast.makeText(activity, "SALVATO CON SUCCESSO", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view!!).navigate(R.id.action_trovatoFragment_to_ListTrovatoFragment)
            }

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
        if (id == R.id.Logout_Item)
        {
           Logged("LOGOUT","","","","","","","","","")
            Navigation.findNavController(view!!).navigate(R.id.action_trovatoFragment_to_registration)

        }
        if (id == R.id.Profilo_Item)
        {
            //do your action here, im just showing toast
            Toast.makeText(context, "PROFILO", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view!!).navigate(R.id.action_trovatoFragment_to_profiloFragment)

        }

        if (id == R.id.Home_Item)
        {
            //do your action here, im just showing toast

            Navigation.findNavController(view!!).navigate(R.id.action_trovatoFragment_to_homeFragment)

        }




        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)



            menu!!.findItem(R.id.Login_Item).isVisible=false
            menu.findItem(R.id.SignIn_Item).isVisible=false
            menu.findItem(R.id.Profilo_Item).isVisible=true
            menu.findItem(R.id.Logout_Item).isVisible=true
            menu.findItem(R.id.Home_Item).isVisible=true






    }




}
















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
import kotlinx.android.synthetic.main.fragment_profilo.*
import java.util.*


class ProfiloFragment : Fragment() {

    var uriImmagineProfilo : Uri? = null
    var clickImg            : Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profilo, container, false) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        showParFirebase()

        textViewModProfilo.setOnClickListener {

            mod_vis(true)


        }

        circleImageView_Profilo.setOnClickListener {
            if(clickImg)
                galleriaImmagineProfilo()
            
        }

        if(textView_ShowSesso.text.toString()=="Maschio")
        {
            radioButton_MaschioProfilo.isChecked = true
            radioButton_FemminaProfilo.isChecked = false
        }


        if (textView_ShowSesso.text.toString()=="Femmina")
        {
            radioButton_FemminaProfilo.isChecked =true
            radioButton_MaschioProfilo.isChecked =false

        }




        buttonSALVA.setOnClickListener {
            uploadImmagineProfilo()

            mod_vis(false)



            }

        buttonANNULLA.setOnClickListener {
            mod_vis(false)

        }

        button_MieiCaniTrovati.setOnClickListener {
            Passaggio("trovati")
            Navigation.findNavController(view).navigate(R.id.action_profiloFragment_to_mieiCaniFragment) }

        button_MieiCaniSmarriti.setOnClickListener {
            Passaggio("smarriti")
            Navigation.findNavController(view).navigate(R.id.action_profiloFragment_to_mieiCaniFragment)

        }













            button_Logout.setOnClickListener {

            Logged("LOGOUT","","","","","","","","","","")
            Navigation.findNavController(view).navigate(R.id.action_profiloFragment_to_registration)
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
            Logged("LOGOUT","","","","","","","","","","")
            Navigation.findNavController(view!!).navigate(R.id.action_profiloFragment_to_registration)

        }


        if (id == R.id.Home_Item)
        {
            Navigation.findNavController(view!!).navigate(R.id.action_profiloFragment_to_homeFragment)

        }






        return super.onOptionsItemSelected(item)
    }


    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)



            menu!!.findItem(R.id.Login_Item).isVisible=false
            menu.findItem(R.id.SignIn_Item).isVisible=false
            menu.findItem(R.id.Profilo_Item).isVisible=false
            menu.findItem(R.id.Logout_Item).isVisible=true
            menu.findItem(R.id.Home_Item).isVisible=true





    }


   private fun modificaProfilo(imgUrl : String)
    {
        FirebaseDatabase.getInstance().getReference()
        val mod_Nome        : String        =   editText_NomeCreaProfilo.text.toString()
        val mod_Cognome     : String        =   editText_CognomeCreaProfilo.text.toString()
        val mod_Eta         : String        =   editText_EtaCreaProfilo.text.toString()
        var mod_Sesso       : String        =   ""
        val mod_Email       : String        =   editText_EmailCreaProfilo.text.toString()
        val mod_Cellulare   : String        =   editText_CellulareCreaProfilo.text.toString()
        val mod_Username    : String        =   editText_UsernameCreaProfilo.text.toString()
        val mod_UriImage    : String        =   imgUrl

        if (radioButton_MaschioProfilo.isChecked)
        {
            mod_Sesso="Maschio"
        }

        if (radioButton_FemminaProfilo.isChecked)
        {
            mod_Sesso="Femmina"
        }

        val user = User(mod_Nome, mod_Cognome,mod_Eta,mod_Email,mod_Sesso,mod_Cellulare, Logged("PASSWORD","","","","","","","","","","")!!,mod_Username,Logged("ID","","","","","","","","","","")!!,mod_UriImage)

        FirebaseDatabase.getInstance().reference.child("User").child(textView_ShowID.text.toString()).setValue(user)
            .addOnSuccessListener {

                Toast.makeText(context,"User Modificato",Toast.LENGTH_SHORT).show()
                Logged("LOGIN",mod_Nome,mod_Cognome,mod_Sesso,mod_Eta,mod_Email,mod_Username,mod_Cellulare,textView_ShowID.text.toString(),mod_UriImage,user.password)
                showParFirebase()
            }




    }

    private fun mod_vis(boolean: Boolean)
    {
        if(boolean) // MODIFICA
        {
            clickImg=true

            editText_NomeCreaProfilo.visibility = View.VISIBLE
            editText_CognomeCreaProfilo.visibility = View.VISIBLE
            editText_EtaCreaProfilo.visibility = View.VISIBLE
            RadioGroup_SessoProfilo.visibility = View.VISIBLE
            editText_EmailCreaProfilo.visibility = View.VISIBLE
            editText_CellulareCreaProfilo.visibility = View.VISIBLE
            editText_UsernameCreaProfilo.visibility = View.VISIBLE

            textView_ShowNome.visibility = View.INVISIBLE
            textView_ShowCognome.visibility = View.INVISIBLE
            textView_ShowEta.visibility = View.INVISIBLE
            textView_ShowSesso.visibility = View.INVISIBLE
            textView_ShowEmail.visibility = View.INVISIBLE
            textView_ShowCellulare.visibility = View.INVISIBLE
            textView_ShowUsername.visibility = View.INVISIBLE



            textViewModProfilo.visibility = View.INVISIBLE

            buttonSALVA.visibility = View.VISIBLE
            buttonANNULLA.visibility = View.VISIBLE

            button_MieiCaniTrovati.visibility=View.INVISIBLE
            button_MieiCaniSmarriti.visibility=View.INVISIBLE
            button_Impostazioni.visibility=View.INVISIBLE
            button_Logout.visibility=View.INVISIBLE

            editText_NomeCreaProfilo.setText(textView_ShowNome.text.toString())
            editText_CognomeCreaProfilo.setText(textView_ShowCognome.text.toString())
            editText_EtaCreaProfilo.setText(textView_ShowEta.text.toString())
            editText_EmailCreaProfilo.setText(textView_ShowEmail.text.toString())
            editText_CellulareCreaProfilo.setText(textView_ShowCellulare.text.toString())
            editText_UsernameCreaProfilo.setText(textView_ShowUsername.text.toString())

        }

        else    //VISUALIZZA
        {
            clickImg=false

            editText_NomeCreaProfilo.visibility=View.INVISIBLE
            editText_CognomeCreaProfilo.visibility=View.INVISIBLE
            editText_EtaCreaProfilo.visibility=View.INVISIBLE
            RadioGroup_SessoProfilo.visibility=View.INVISIBLE
            editText_EmailCreaProfilo.visibility=View.INVISIBLE
            editText_CellulareCreaProfilo.visibility=View.INVISIBLE
            editText_UsernameCreaProfilo.visibility=View.INVISIBLE

            textView_ShowNome.visibility=View.VISIBLE
            textView_ShowCognome.visibility=View.VISIBLE
            textView_ShowEta.visibility=View.VISIBLE
            textView_ShowSesso.visibility=View.VISIBLE
            textView_ShowEmail.visibility=View.VISIBLE
            textView_ShowCellulare.visibility=View.VISIBLE
            textView_ShowUsername.visibility=View.VISIBLE

            button_MieiCaniTrovati.visibility=View.VISIBLE
            button_MieiCaniSmarriti.visibility=View.VISIBLE
            button_Impostazioni.visibility=View.VISIBLE
            button_Logout.visibility=View.VISIBLE


            textViewModProfilo.visibility=View.VISIBLE

            buttonSALVA.visibility=View.INVISIBLE
            buttonANNULLA.visibility=View.INVISIBLE
        }



    }

    private fun showParFirebase()
    {



            textView_ShowNome.text      = Logged("NOME", "", "", "", "", "", "", "", "", "","")
            textView_ShowCognome.text   = Logged("COGNOME", "", "", "", "", "", "", "", "", "","")
            textView_ShowSesso.text     = Logged("SESSO", "", "", "", "", "", "", "", "", "","")
            textView_ShowEta.text       = Logged("ETA", "", "", "", "", "", "", "", "", "","")
            textView_ShowEmail.text     = Logged("EMAIL", "", "", "", "", "", "", "", "", "","")
            textView_ShowCellulare.text = Logged("CELLULARE", "", "", "", "", "", "", "", "", "","")
            textView_ShowUsername.text  = Logged("USERNAME", "", "", "", "", "", "", "", "", "","")
            textView_ShowID.text        = Logged("ID", "", "", "", "", "", "", "", "", "","")

            val img             = Logged("URIIMAGE", "", "", "", "", "", "", "", "", "","")


            if (img?.trim() != "")
                Picasso.get().load(img).into(circleImageView_Profilo)
            else
                Picasso.get().load(R.drawable.com_facebook_profile_picture_blank_portrait).into(
                    circleImageView_Profilo
                )

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

    private fun uploadImmagineProfilo()
    {

        if (uriImmagineProfilo==null)
        {   modificaProfilo(Logged("URIIMAGE","","","","","","","","","","")!!) }

        else
        {
            val filename= UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/images/user/$filename")
            ref.putFile(uriImmagineProfilo!!).addOnSuccessListener {

                ref.downloadUrl.addOnSuccessListener {
                    modificaProfilo(it.toString())

                }

            }



        }
    }

}

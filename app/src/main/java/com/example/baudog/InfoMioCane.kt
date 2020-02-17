package com.example.baudog

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_info_cane.*
import kotlinx.android.synthetic.main.fragment_info_mio_cane.*
import kotlinx.android.synthetic.main.fragment_profilo.*
import java.util.*


class InfoMioCane : Fragment() {

    var ID_DOG =""
    var uriImmagineProfilo : Uri? = null
    var clickImg = false
    var precedentImage : String? =""
    val smarr_trov = Passaggio("QUI")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_mio_cane, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments.let {

            val cane:Cane? = it!!.getParcelable("cane")
            cane?.let { it ->

                if (it.colore_collare.trim()!="NULL")
                {
                    textView_ViewNomeMio.text=it.nome_collare
                }

                else
                {textView_ViewNomeMio.text="ASSENTE"}

                ID_DOG=it.id


                textView_ViewRazzaMio.text=it.razza

                textView_ViewSessoMio.text=it.sesso

                textView_ViewColoreMio.text=it.colore

                if (it.colore_collare.trim()!="NULL")
                {textView_ViewCollareMio.text=it.colore_collare}
                else
                {textView_ViewCollareMio.text="ASSENTE"}


                if (it.chip)
                {textView_ViewChipMio.text="PRESENTE"}
                else
                {textView_ViewChipMio.text="ASSENTE"}

                textView_ViewInfoMio.text=it.info


                if (it.profileImageUrl?.trim() != "")
                {
                    Picasso.get().load(it.profileImageUrl).into(UriImageMio)
                    precedentImage=it.profileImageUrl
                }

                if (it.profileImageUrl== "")
                {
                    UriImageMio!!.setImageResource(R.drawable.prova1)
                }
            }
        }


        UriImageMio.setOnClickListener{
            if(clickImg)
                galleriaImmagineProfilo()
        }


        //SESSO CHECKED IN MOD --------------------------------------------------------------
        if(textView_ViewSessoMio.text.toString()=="Maschio")
        {
            radioButton_MaschioCaneMod.isChecked = true
            radioButton_FemminaCaneMod.isChecked = false
        }
        if (textView_ViewSessoMio.text.toString()=="Femmina")
        {
            radioButton_FemminaCaneMod.isChecked =true
            radioButton_MaschioCaneMod.isChecked =false

        }


        //COLLARE PRESENTE CHEKED IN MOD --------------------------------------------------------------
        if(textView_ViewCollareMio.text.toString()=="ASSENTE")
        {

            radioButton_CollareAssenteCaneMod.isChecked = true
            radioButton_CollarePresenteCaneMod.isChecked = false
        }
        if(textView_ViewCollareMio.text.toString()!="ASSENTE")
        {
            radioButton_CollareAssenteCaneMod.isChecked = false
            radioButton_CollarePresenteCaneMod.isChecked = true
        }

        //CHIP PRESENTE CHEKED IN MOD --------------------------------------------------------------
        if(textView_ViewChipMio.text.toString()=="PRESENTE")
        {
            radioButton_ChipPresenteCaneMod.isChecked = true
            radioButton_ChipAssenteCaneMod.isChecked = false
        }
        if(textView_ViewChipMio.text.toString()=="ASSENTE")
        {
            radioButton_ChipPresenteCaneMod.isChecked = false
            radioButton_ChipAssenteCaneMod.isChecked = true
        }

        radioButton_CollarePresenteCaneMod.setOnClickListener{
            editText_ColoreCollareCaneMod.visibility=View.VISIBLE
        }

        radioButton_CollareAssenteCaneMod.setOnClickListener{
            editText_ColoreCollareCaneMod.visibility = View.GONE
        }


        //BOTTONI --------------------------------------------------------------
        buttonModificaCane.setOnClickListener {

            mod_vis(true)

        }

        buttonEliminaCane.setOnClickListener { eliminaCane() }


        buttonSalvaCaneMod.setOnClickListener {
            uploadImmagineProfilo()
            mod_vis(false) }

        buttonAnnullaCaneMod.setOnClickListener {
            mod_vis(false)
        }
    }


    private fun mod_vis(boolean: Boolean)
    {
        if (boolean) // MODIFICA
        {
            clickImg = true

            editText_NomeCaneMod.visibility = View.VISIBLE
            editText_RazzaCaneMod.visibility = View.VISIBLE
            RadioGroup_ChipCaneMod.visibility = View.VISIBLE
            RadioGroup_CollareCaneMod.visibility = View.VISIBLE
            RadioGroup_SessoCaneMod.visibility = View.VISIBLE
            editText_ColoreCaneMod.visibility = View.VISIBLE
            editText_InfoCaneMod.visibility = View.VISIBLE

            textView_ViewNomeMio.visibility = View.INVISIBLE
            textView_ViewRazzaMio.visibility = View.INVISIBLE
            textView_ViewSessoMio.visibility = View.INVISIBLE
            textView_ViewColoreMio.visibility = View.INVISIBLE
            textView_ViewCollareMio.visibility = View.INVISIBLE
            textView_ViewChipMio.visibility = View.INVISIBLE
            textView_ViewInfoMio.visibility = View.INVISIBLE



            buttonSalvaCaneMod.visibility = View.VISIBLE
            buttonAnnullaCaneMod.visibility = View.VISIBLE

            buttonEliminaCane.visibility = View.INVISIBLE
            buttonModificaCane.visibility = View.INVISIBLE


            editText_NomeCaneMod.setText(textView_ViewNomeMio.text.toString())
            editText_RazzaCaneMod.setText(textView_ViewRazzaMio.text.toString())
            editText_ColoreCaneMod.setText(textView_ViewColoreMio.text.toString())
            editText_InfoCaneMod.setText(textView_ViewInfoMio.text.toString())


        }
        else    //VISUALIZZA
        {
            clickImg = false

            editText_NomeCaneMod.visibility = View.INVISIBLE
            editText_RazzaCaneMod.visibility = View.INVISIBLE
            RadioGroup_ChipCaneMod.visibility = View.INVISIBLE
            RadioGroup_CollareCaneMod.visibility = View.INVISIBLE
            RadioGroup_SessoCaneMod.visibility = View.INVISIBLE
            editText_ColoreCaneMod.visibility = View.INVISIBLE
            editText_InfoCaneMod.visibility = View.INVISIBLE
            editText_ColoreCollareCaneMod.visibility= View.GONE

            textView_ViewNomeMio.visibility = View.VISIBLE
            textView_ViewRazzaMio.visibility = View.VISIBLE
            textView_ViewSessoMio.visibility = View.VISIBLE
            textView_ViewColoreMio.visibility = View.VISIBLE
            textView_ViewCollareMio.visibility = View.VISIBLE
            textView_ViewChipMio.visibility = View.VISIBLE
            textView_ViewInfoMio.visibility = View.VISIBLE





            buttonSalvaCaneMod.visibility = View.INVISIBLE
            buttonAnnullaCaneMod.visibility = View.INVISIBLE

            buttonEliminaCane.visibility = View.VISIBLE
            buttonModificaCane.visibility = View.VISIBLE


        }
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

            Picasso.get().load(uriImmagineProfilo).into(UriImageMio)


        }
    }

    private fun uploadImmagineProfilo()
    {

        if (uriImmagineProfilo==null)
        {   modificaCane("") }

        else
        {
            val filename= UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/images/cani/$filename")
            ref.putFile(uriImmagineProfilo!!).addOnSuccessListener {

                ref.downloadUrl.addOnSuccessListener {
                    modificaCane(it.toString())

                }

            }



        }
    }




    var mod_UriImage: String       ? = ""

    private fun modificaCane(imgUrl : String)
    {
        FirebaseDatabase.getInstance().reference

        val mod_Nome            : String        =   editText_NomeCaneMod.text.toString()
        val mod_Razza           : String        =   editText_RazzaCaneMod.text.toString()
        var mod_Sesso           : String        =   ""
        val mod_Colore          : String        =   editText_ColoreCaneMod.text.toString()
        var mod_Collare         : Boolean       =   true
        var mod_ColoreCollare   : String        =   ""
        var mod_Chip            : Boolean       =   true
        val mod_Info            : String        =   editText_InfoCaneMod.text.toString()

        if (imgUrl!="") {mod_UriImage =   imgUrl}
        else    {mod_UriImage=precedentImage}


        if (radioButton_MaschioCaneMod.isChecked)
        {
            mod_Sesso="Maschio"
        }
        if (radioButton_FemminaCaneMod.isChecked)
        {
            mod_Sesso="Femmina"
        }

        if(radioButton_CollarePresenteCaneMod.isChecked)
        {
            mod_Collare=true
            mod_ColoreCollare = editText_ColoreCollareCaneMod.text.toString()
        }
        if(radioButton_CollareAssenteCaneMod.isChecked)
        {
            mod_Collare=false
            mod_ColoreCollare = "ASSENTE"
        }

        if(radioButton_ChipPresenteCaneMod.isChecked)
        {
            mod_Chip=true
        }

        if (radioButton_ChipAssenteCaneMod.isChecked)
        {
            mod_Chip=false
        }



        val cane = Cane(ID_DOG,mod_Razza,mod_Sesso,mod_Colore,mod_Chip,mod_Collare,mod_ColoreCollare,mod_Nome,mod_Info,mod_UriImage)

        FirebaseDatabase.getInstance().reference.child("cani/$smarr_trov").child(ID_DOG).setValue(cane)
            .addOnSuccessListener {

                Toast.makeText(context,"Cane Modificato", Toast.LENGTH_SHORT).show()
                showPar()
            }




    }

    private fun showPar()
    {
        textView_ViewNomeMio.text = editText_NomeCaneMod.text.toString()
        textView_ViewRazzaMio.text = editText_RazzaCaneMod.text.toString()
        if(radioButton_MaschioCaneMod.isChecked) textView_ViewSessoMio.text="Maschio"
        if(radioButton_FemminaCaneMod.isChecked) textView_ViewSessoMio.text="Femmina"
        textView_ViewColoreMio.text = editText_ColoreCaneMod.text.toString()
        if (radioButton_CollareAssenteCaneMod.isChecked) textView_ViewCollareMio.text="ASSENTE"
        if (radioButton_CollarePresenteCaneMod.isChecked)textView_ViewCollareMio.text=editText_ColoreCollareCaneMod.text.toString()
        if (radioButton_ChipAssenteCaneMod.isChecked) textView_ViewCollareMio.text="ASSENTE"
        if (radioButton_ChipPresenteCaneMod.isChecked) textView_ViewCollareMio.text="PRESENTE"

        textView_ViewInfoMio.text=editText_InfoCaneMod.text.toString()
        val img = mod_UriImage

        if (img?.trim() != "")
            Picasso.get().load(img).into(UriImageMio)
        else
            Picasso.get().load(R.drawable.prova1).into(UriImageMio)
    }

    private fun eliminaCane()
    {
        FirebaseDatabase.getInstance().reference.child("cani/$smarr_trov").child(ID_DOG).removeValue()
            .addOnSuccessListener { Navigation.findNavController(view!!).navigateUp() }
    }
}

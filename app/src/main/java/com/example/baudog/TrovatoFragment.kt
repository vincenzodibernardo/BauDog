package com.example.baudog

import android.R.layout.simple_list_item_1
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_trovato.*

class TrovatoFragment : Fragment() {

    private var chipValue       :Boolean   =true
    private var collareValue    :Boolean   =true
    private var sesso           :String    ="Maschio"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trovato, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        // Serve a far mostrare la lista delle razze dei cani
        val res: Resources = resources
        val Razze = res.getStringArray(R.array.razze)
        val adapter = ArrayAdapter(context!!, simple_list_item_1, Razze)

        Multi_SelezionaRazza.threshold = 0
        Multi_SelezionaRazza.setAdapter(adapter)
        Multi_SelezionaRazza.setOnClickListener { Multi_SelezionaRazza.showDropDown() }


        radioButton_CollareSi.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked) {
                editText_ColoreCollare.text = null
                editText_NomeCollare.text = null
                editText_ColoreCollare.text.insert(0, "NULL")
                editText_NomeCollare.text.insert(0, "NULL")
                editText_NomeCollare.visibility = View.GONE
                editText_ColoreCollare.visibility = View.GONE
            }


        }


        radioButton_CollareNo.setOnCheckedChangeListener { buttonView, isChecked ->

            if (!isChecked) {
                editText_ColoreCollare.text = null
                editText_NomeCollare.text = null
                editText_ColoreCollare.visibility = View.VISIBLE
                editText_NomeCollare.visibility = View.VISIBLE
            }

        }


        button_Salva.setOnClickListener {
            if (campiValidi())
            {

                salvaCane()

            }


        }

        button_Annulla.setOnClickListener {
            Navigation.findNavController(it).navigateUp()

        }

        radioButton_ChipSi.setOnClickListener { chipValue =true }
        radioButton_ChipNo.setOnClickListener { chipValue=false }
        radioButton_CollareNo.setOnClickListener { collareValue=false }
        radioButton_CollareSi.setOnClickListener { collareValue=true}
        radioButton_Femmina.setOnClickListener { sesso="Femmina" }
        radioButton_Maschio.setOnClickListener { sesso="Maschio" }


    }

    private fun campiValidi(): Boolean
    {

        var errore = false

        if (Multi_SelezionaRazza.text.toString().trim().isEmpty())
        {
            Multi_SelezionaRazza.error = "Riempire il campo \'razza\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore=true
        }


        if (editText_Colore.text.toString().trim().isEmpty())
        {
            editText_Colore.error = "Riempire il campo \'colore\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore=true
        }

        if (editText_ColoreCollare.text.toString().trim().isEmpty())
        {
            editText_ColoreCollare.error = "Riempire il campo \'colore collare\'"
            Toast.makeText(activity, "Riempi tutti i campi", Toast.LENGTH_SHORT).show()
            errore=true
        }

        if (errore) {return false}

        return true

    }

    private fun salvaCane()
    {
        val db_Razza:String =Multi_SelezionaRazza.text.toString()
        val db_Colore:String =editText_Colore.text.toString()
        val db_Sesso:String =sesso
        val db_Chip:Boolean =chipValue
        val db_Collare:Boolean =collareValue
        val db_ColoreCollare:String =editText_ColoreCollare.text.toString()
        val db_NomeCollare:String =editText_NomeCollare.text.toString()
        val db_Info:String =editText_InfoAggiuntive.text.toString()




        val ref =FirebaseDatabase.getInstance().getReference("CANI")
        val db_caneID:String= ref.push().key.toString()

        val cane = Cane(db_caneID,db_Razza,db_Sesso,db_Colore,db_Chip,db_Collare,db_ColoreCollare,db_NomeCollare,db_Info)

        ref.child("Vincenzo").setValue(cane).addOnCompleteListener { Toast.makeText(activity, "SALVATO",Toast.LENGTH_LONG).show() }
    }


}



















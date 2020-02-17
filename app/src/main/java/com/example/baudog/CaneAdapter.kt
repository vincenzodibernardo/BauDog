package com.example.baudog

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso

class CaneAdapter(val context: Context?, val CaneList: ArrayList<Cane>, val myDog : Boolean) : RecyclerView.Adapter<CaneAdapter.Holder>()
{

    override fun onBindViewHolder(holder: Holder, position: Int)
    {
        val cane = CaneList.get(position)
        holder.bind(CaneList[position], context!!)

        holder.itemView.setOnClickListener {


            // Creo un bundle e vi inserisco la birra da visualizzare
            val b = Bundle()
            b.putParcelable("cane", cane)     //TODO: Il nome dell'ogggetto andrebbe inserito in un solo punto!!

            if (myDog)
            {
                Navigation.findNavController(it).navigate(R.id.action_mieiCaniFragment_to_infoMioCane, b)
            }


            else
            {
                Navigation.findNavController(it).navigate(R.id.action_ListTrovatoFragment_to_infoCane, b)
            }



        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
    {
        val view = LayoutInflater.from(context).inflate(R.layout.riga_cane, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int
    {
        return CaneList.size
    }


    inner class Holder(view: View?) : RecyclerView.ViewHolder(view!!)
    {
        val recordRazza = view?.findViewById<TextView>(R.id.textView_showRazza)
        val recordSesso = view?.findViewById<TextView>(R.id.textView_showSesso)
        var recordImage = view?.findViewById<ImageView>(R.id.imageView_showCane)
        val recordNome = view?.findViewById<TextView>(R.id.textView_showNome)

        fun bind(cane: Cane, context: Context)
        {

            recordRazza?.text = cane.razza
            recordSesso?.text = cane.sesso
            recordNome?.text  = cane.nome_collare





            val indirizzo :String? = cane.profileImageUrl



            if (indirizzo!!.trim().isNotEmpty())
            {
                Picasso.get().load(indirizzo).into(recordImage)
            }






            if (indirizzo.trim().isEmpty())
            {
                recordImage!!.setImageResource(R.drawable.prova1)
            }

            if ((recordNome?.text.toString().trim().isEmpty())||(recordNome?.text.toString().trim()=="NULL"))
            {
                recordNome?.visibility=View.GONE
            }


        }
    }
}

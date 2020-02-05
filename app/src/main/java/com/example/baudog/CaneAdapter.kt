package com.example.baudog

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class CaneAdapter(val context: Context?, val CaneList: ArrayList<Cane>) : RecyclerView.Adapter<CaneAdapter.Holder>()
{

    override fun onBindViewHolder(holder: Holder, position: Int)
    {
        holder.bind(CaneList[position], context!!)
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
        val recordImage = view?.findViewById<ImageView>(R.id.imageView_showCane)
        val recordNome = view?.findViewById<TextView>(R.id.textView_showNome)

        fun bind(cane: Cane, context: Context)
        {
            val indirizzo :String

            recordRazza?.text = cane.razza
            recordSesso?.text = cane.sesso
            recordNome?.text=cane.nome_collare

            indirizzo=cane.profileImageUrl

            if (indirizzo.trim().isNotEmpty())
            {
                Picasso.get().load(indirizzo).into(recordImage)
            }

            if ((recordNome?.text.toString().trim().isEmpty())||(recordNome?.text.toString().trim()=="NULL"))
            {
                recordNome?.visibility=View.GONE
            }


        }
    }
}

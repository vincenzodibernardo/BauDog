package com.example.baudog

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CaneAdapter(val context: Context, val CaneList: ArrayList<Cane>) :
    RecyclerView.Adapter<CaneAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(CaneList[position], context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.riga_cane, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return CaneList.size
    }


    inner class Holder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val recordRazza = view?.findViewById<TextView>(R.id.textView_showRazza)
        val recordSesso = view?.findViewById<TextView>(R.id.textView_showSesso)

        fun bind(cane: Cane, context: Context) {
            recordRazza?.text = cane.razza
            recordSesso?.text = cane.sesso

        }
    }
}

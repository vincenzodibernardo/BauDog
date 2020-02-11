package com.example.baudog

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_info_cane.*


class InfoCane : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_cane, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments.let {

            val cane:Cane? = it!!.getParcelable("cane")
            cane?.let {

                if (it.colore_collare.trim()!="NULL")
                {textView_ViewNome.text=it.nome_collare
                    }
                else
                {textView_ViewNome.text="ASSENTE"}


                textView_ViewRazza.text=it.razza

                textView_ViewSesso.text=it.sesso

                textView_ViewColore.text=it.colore

                if (it.colore_collare.trim()!="NULL")
                    {textView_ViewCollare.text=it.colore_collare}
                else
                    {textView_ViewCollare.text="ASSENTE"}

                if (it.chip)
                    {textView_ViewChip.text="PRESENTE"}
                else
                    {textView_ViewChip.text="ASSENTE"}

                textView_ViewInfo.text=it.info

                Picasso.get().load(it.profileImageUrl).into(UriImage)

            }
        }

    }
}

package com.example.baudog

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_list_ritrovamento.*
import kotlin.math.log


class ListTrovatoFragment : Fragment() {


    var fragmentView : View? = null
    var firedatabase : FirebaseDatabase? = null
    var CaneListTrovato : ArrayList<Cane> ? = null
    var ref : DatabaseReference? = null

    var mRecyclerView : RecyclerView? =null

    var smarr_trov : String = Passaggio("QUI")

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        fragmentView= LayoutInflater.from(activity).inflate(R.layout.fragment_list_ritrovamento, container, false)

        firedatabase = FirebaseDatabase.getInstance()


        mRecyclerView = fragmentView?.findViewById(R.id.caneListTrovato)
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.layoutManager = LinearLayoutManager(context)



        CaneListTrovato= arrayListOf<Cane>()
        ref = FirebaseDatabase.getInstance().getReference("cani/$smarr_trov")

        Log.d("CHECCO", "PASSAGGIO 2 : cani/$smarr_trov")




        ref?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }



            override fun onDataChange(p0: DataSnapshot)
            {
                CaneListTrovato!!.clear()


                if(p0.exists())
                {
                    for (h in p0.children)
                    {
                        val cane = h.getValue(Cane::class.java)
                        CaneListTrovato?.add(cane!!)
                    }

                    val adapter = CaneAdapter(context, CaneListTrovato!!,"Trovato")
                    mRecyclerView?.adapter = adapter


                }

            }
        })

        return  fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView_Smarr_Trov.text=smarr_trov.toUpperCase()

        Log.d("CERCA",smarr_trov)
    }









}

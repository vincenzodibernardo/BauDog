package com.example.baudog

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*


class ListTrovatoFragment : Fragment() {


    var fragmentView : View? = null
    var firedatabase : FirebaseDatabase? = null
    var CaneList : ArrayList<Cane> ? = null
    var ref : DatabaseReference? = null

    var mRecyclerView : RecyclerView? =null

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        fragmentView= LayoutInflater.from(activity).inflate(R.layout.fragment_list_ritrovamento, container, false)

        firedatabase = FirebaseDatabase.getInstance()


        mRecyclerView = fragmentView?.findViewById(R.id.caneList)
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.layoutManager = LinearLayoutManager(context)


        CaneList= arrayListOf<Cane>()
        ref = FirebaseDatabase.getInstance().getReference("cani/trovati")



        ref?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }



            override fun onDataChange(p0: DataSnapshot)
            {
                CaneList!!.clear()


                if(p0.exists())
                {
                    for (h in p0.children)
                    {
                        val cane = h.getValue(Cane::class.java)
                        CaneList?.add(cane!!)
                    }

                    val adapter = CaneAdapter(context, CaneList!!)
                    mRecyclerView?.adapter = adapter

                }

            }
        })

        return  fragmentView
    }






}

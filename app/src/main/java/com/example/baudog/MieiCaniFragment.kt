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
import kotlinx.android.synthetic.main.fragment_miei_cani.*


class MieiCaniFragment : Fragment() {

    var fragmentView : View? = null
    var firedatabase : FirebaseDatabase? = null
    var CaneListMiei : ArrayList<Cane> ? = null
    var ref : DatabaseReference? = null

    var mRecyclerView : RecyclerView? =null

    var smarr_trov : String = Passaggio("QUI")


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            fragmentView = LayoutInflater.from(activity).inflate(R.layout.fragment_miei_cani, container, false)


            firedatabase = FirebaseDatabase.getInstance()


            mRecyclerView = fragmentView?.findViewById(R.id.caneListMiei)
            mRecyclerView?.setHasFixedSize(true)
            mRecyclerView?.layoutManager = LinearLayoutManager(context)



            CaneListMiei= arrayListOf<Cane>()
            ref = FirebaseDatabase.getInstance().getReference("cani/$smarr_trov")




            ref?.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }



                override fun onDataChange(p0: DataSnapshot)
                {
                    CaneListMiei!!.clear()


                    if(p0.exists())
                    {
                        for (h in p0.children)
                        {
                            val cane = h.getValue(Cane::class.java)
                            val caneId = cane!!.id

                            val userId: List<String> = caneId.split("@")

                            Log.d("USER_ID: ", "CODICE_ID : $userId")
                            if(userId[0]== Logged("ID","","","","","","","","","",""))
                            {
                                CaneListMiei?.add(cane)
                            }

                        }

                        val adapter = CaneAdapter(context, CaneListMiei!!,true)
                        mRecyclerView?.adapter = adapter


                    }

                }
            })

            return  fragmentView
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (smarr_trov=="smarriti")
            {
                textView_Miei.text = "I MIEI CANI : SMARRITI"
            }

        if (smarr_trov=="trovati")
        {
            textView_Miei.text = "I MIEI CANI : TROVATI"
        }
    }




}

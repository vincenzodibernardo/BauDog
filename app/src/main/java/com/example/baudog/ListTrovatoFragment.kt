package com.example.baudog

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_list_ritrovamento.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlin.math.log


class ListTrovatoFragment : Fragment() {


    var fragmentView : View? = null
    var firedatabase : FirebaseDatabase? = null
    var CaneListTrovato : ArrayList<Cane> ? = null
    var ref : DatabaseReference? = null

    var mRecyclerView : RecyclerView? =null

    var smarr_trov : String = Passaggio("QUI")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)
    }

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

                    val adapter = CaneAdapter(context, CaneListTrovato!!)
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
            Logged("LOGOUT","","","","","","","","","")


        }
        if (id == R.id.Profilo_Item)
        {
            //do your action here, im just showing toast
            Toast.makeText(context, "PROFILO", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view!!).navigate(R.id.action_trovatoFragment_to_profiloFragment)

        }

        if (id == R.id.Login_Item)
        {
            //do your action here, im just showing toast
            Toast.makeText(context, "LOGIN", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view!!).navigate(R.id.action_ListTrovatoFragment_to_registration)


        }

        if (id == R.id.SignIn_Item)
        {
            //do your action here, im just showing toast
            Toast.makeText(context, "SIGN IN", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view!!).navigate(R.id.action_ListTrovatoFragment_to_creaUserFragment)

        }

        if (id == R.id.Home_Item)
        {
            Navigation.findNavController(view!!).navigate(R.id.action_ListTrovatoFragment_to_homeFragment)
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)


        if (logged=="Y")
        {
            menu!!.findItem(R.id.Login_Item).isVisible=false
            menu.findItem(R.id.SignIn_Item).isVisible=false
            menu.findItem(R.id.Profilo_Item).isVisible=true
            menu.findItem(R.id.Logout_Item).isVisible=true


        }

        else
        {
            menu!!.findItem(R.id.Login_Item).isVisible=true
            menu.findItem(R.id.SignIn_Item).isVisible=true
            menu.findItem(R.id.Profilo_Item).isVisible=false
            menu.findItem(R.id.Logout_Item).isVisible=false
        }

        menu.findItem(R.id.Impostazioni_Item).isVisible=true
        menu.findItem(R.id.Home_Item).isVisible=true

    }









}

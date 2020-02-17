package com.example.baudog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_info_cane.*





class InfoCane : Fragment() {

    var ID_DOG : String =""
    var NumeroCellulare =""
    var Email           =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)


    }

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

            val cane: Cane? = it!!.getParcelable("cane")
            cane?.let { it ->

                if (it.colore_collare.trim() != "NULL") {
                    textView_ViewNome.text = it.nome_collare
                } else {
                    textView_ViewNome.text = "ASSENTE"
                }

                ID_DOG=it.id


                textView_ViewRazza.text = it.razza

                textView_ViewSesso.text = it.sesso

                textView_ViewColore.text = it.colore

                if (it.colore_collare.trim() != "NULL") {
                    textView_ViewCollare.text = it.colore_collare
                } else {
                    textView_ViewCollare.text = "ASSENTE"
                }


                if (it.chip) {
                    textView_ViewChip.text = "PRESENTE"
                } else {
                    textView_ViewChip.text = "ASSENTE"
                }

                textView_ViewInfo.text = it.info


                if (it.profileImageUrl?.trim() != "")
                    Picasso.get().load(it.profileImageUrl).into(UriImage)

                if (it.profileImageUrl == "") {
                    UriImage!!.setImageResource(R.drawable.prova1)
                }
            }
        }



            buttonChiama.setOnClickListener {

                val caneIdControl: List<String> = ID_DOG.split("@")
                val cancan = caneIdControl[0]

                var firedatabase  = FirebaseDatabase.getInstance()
                var ref = FirebaseDatabase.getInstance().getReference("User/")

                ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot)
                    {
                        for (h in p0.children)
                        {
                            val user: User? = h.getValue(User::class.java)
                            val userId = user!!.ID
                            Log.d("USERID", "USER : $userId")
                            Log.d("USERID", "CANE : $cancan")

                            if (cancan == userId)
                            {
                                NumeroCellulare= user.cellulare
                                chiamaNumero()


                            }
                        }


                    }
                })






            }

        buttonEmail.setOnClickListener {
            val caneIdControl: List<String> = ID_DOG.split("@")
            val cancan = caneIdControl[0]
            var firedatabase  = FirebaseDatabase.getInstance()
            var ref = FirebaseDatabase.getInstance().getReference("User/")

            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot)
                {
                    for (h in p0.children)
                    {
                        val user: User? = h.getValue(User::class.java)
                        val userId = user!!.ID
                        Log.d("USERID", "USER : $userId")
                        Log.d("USERID", "CANE : $cancan")

                        if (cancan == userId)
                        {
                            Email= user.email
                            sendEmail()


                        }
                    }


                }
            })
        }

    }



        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
            Log.d("MENUOPT", "ENTRO IN OnCreateOptionMenu")
            inflater!!.inflate(R.menu.main_menu, menu)
            super.onCreateOptionsMenu(menu, inflater)

        }

        override fun onOptionsItemSelected(item: MenuItem?): Boolean {


            //get item id to handle item clicks
            val id = item!!.itemId
            //handle item clicks
            if (id == R.id.Logout_Item) {
                Logged("LOGOUT", "", "", "", "", "", "", "", "", "", "")
                Toast.makeText(context, "LOGOUT", Toast.LENGTH_SHORT).show()

            }
            if (id == R.id.Profilo_Item) {
                //do your action here, im just showing toast
                Toast.makeText(context, "PROFILO", Toast.LENGTH_SHORT).show()

                Navigation.findNavController(view!!)
                    .navigate(R.id.action_infoCane_to_profiloFragment)

            }

            if (id == R.id.Login_Item) {
                //do your action here, im just showing toast
                Toast.makeText(context, "LOGIN", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view!!).navigate(R.id.action_infoCane_to_registration)


            }

            if (id == R.id.SignIn_Item) {
                //do your action here, im just showing toast
                Toast.makeText(context, "SIGN IN", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view!!)
                    .navigate(R.id.action_infoCane_to_creaUserFragment)

            }

            if (id == R.id.Home_Item) {
                Navigation.findNavController(view!!).navigate(R.id.action_infoCane_to_homeFragment)
            }

            return super.onOptionsItemSelected(item)
        }

        override fun onPrepareOptionsMenu(menu: Menu?)
        {
            super.onPrepareOptionsMenu(menu)


            if (logged == "Y") {
                menu!!.findItem(R.id.Login_Item).isVisible = false
                menu.findItem(R.id.SignIn_Item).isVisible = false
                menu.findItem(R.id.Profilo_Item).isVisible = true
                menu.findItem(R.id.Logout_Item).isVisible = true


            } else {
                menu!!.findItem(R.id.Login_Item).isVisible = true
                menu.findItem(R.id.SignIn_Item).isVisible = true
                menu.findItem(R.id.Profilo_Item).isVisible = false
                menu.findItem(R.id.Logout_Item).isVisible = false
            }

            menu.findItem(R.id.Home_Item).isVisible = true

        }
    private fun chiamaNumero()
    {
        Log.d("USERID", "TEL : $NumeroCellulare")
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + NumeroCellulare))
        startActivity(intent)


    }

    private fun sendEmail()
    {


        val intent = Intent(Intent.ACTION_VIEW)
        val data = Uri.parse(
            "mailto:"
                    + Email
                    + "?subject=" + "Questo è il mio cane ?" + "&body=" + ""
        )
        intent.data = data
        startActivity(intent)
    }

}


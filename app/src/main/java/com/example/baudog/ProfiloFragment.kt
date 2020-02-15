package com.example.baudog

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profilo.*
import kotlinx.android.synthetic.main.fragment_registration.*


class ProfiloFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profilo, container, false) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Logged("LOGGED","","","","","","","","","")=="Y")
        {

            textView_ShowNome.text = Logged("NOME", "", "", "", "", "", "", "", "", "")
            textView_ShowCognome.text = Logged("COGNOME", "", "", "", "", "", "", "", "", "")
            textView_ShowSesso.text = Logged("SESSO", "", "", "", "", "", "", "", "", "")
            textView_ShowEta.text = Logged("ETA", "", "", "", "", "", "", "", "", "")
            textView_ShowEmail.text = Logged("EMAIL", "", "", "", "", "", "", "", "", "")
            textView_ShowCellulare.text = Logged("CELLULARE", "", "", "", "", "", "", "", "", "")
            textView_ShowID.text = Logged("ID", "", "", "", "", "", "", "", "", "")

            val Img = Logged("URIIMAGE", "", "", "", "", "", "", "", "", "")


            if (Img?.trim() != "")
                Picasso.get().load(Img).into(circleImageView_Profilo)
            else
                Picasso.get().load(R.drawable.com_facebook_profile_picture_blank_portrait).into(
                    circleImageView_Profilo
                )
        }



        textViewModProfilo.setOnClickListener {

            editText_NomeCrea.visibility=View.VISIBLE
            editText_CognomeCrea.visibility=View.VISIBLE
            editText_EtaCrea.visibility=View.VISIBLE
            RadioGroup_Sesso.visibility=View.VISIBLE
            editText_EmailCrea.visibility=View.VISIBLE
            editText_CellulareCrea.visibility=View.VISIBLE

            textView_ShowNome.visibility=View.INVISIBLE
            textView_ShowCognome.visibility=View.INVISIBLE
            textView_ShowEta.visibility=View.INVISIBLE
            textView_ShowSesso.visibility=View.INVISIBLE
            textView_ShowEmail.visibility=View.INVISIBLE
            textView_ShowCellulare.visibility=View.INVISIBLE

            textViewModProfilo.visibility=View.INVISIBLE

            buttonSALVA.visibility=View.VISIBLE
            buttonANNULLA.visibility=View.VISIBLE



        }

        buttonANNULLA.setOnClickListener {
            editText_NomeCrea.visibility=View.INVISIBLE
            editText_CognomeCrea.visibility=View.INVISIBLE
            editText_EtaCrea.visibility=View.INVISIBLE
            RadioGroup_Sesso.visibility=View.INVISIBLE
            editText_EmailCrea.visibility=View.INVISIBLE
            editText_CellulareCrea.visibility=View.INVISIBLE

            textView_ShowNome.visibility=View.VISIBLE
            textView_ShowCognome.visibility=View.VISIBLE
            textView_ShowEta.visibility=View.VISIBLE
            textView_ShowSesso.visibility=View.VISIBLE
            textView_ShowEmail.visibility=View.VISIBLE
            textView_ShowCellulare.visibility=View.VISIBLE

            textViewModProfilo.visibility=View.VISIBLE

            buttonSALVA.visibility=View.INVISIBLE
            buttonANNULLA.visibility=View.INVISIBLE
        }
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
            Navigation.findNavController(view!!).navigate(R.id.action_profiloFragment_to_registration)

        }


        if (id == R.id.Home_Item)
        {
            Navigation.findNavController(view!!).navigate(R.id.action_profiloFragment_to_homeFragment)

        }






        return super.onOptionsItemSelected(item)
    }


    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)



            menu!!.findItem(R.id.Login_Item).isVisible=false
            menu.findItem(R.id.SignIn_Item).isVisible=false
            menu.findItem(R.id.Profilo_Item).isVisible=false
            menu.findItem(R.id.Logout_Item).isVisible=true
            menu.findItem(R.id.Impostazioni_Item).isVisible=true
            menu.findItem(R.id.Home_Item).isVisible=true





    }

}

package com.example.apapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add__rez.*
import kotlinx.android.synthetic.main.novi_prikaz_rez.*

class Add_Rez : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__rez)

        val intent = intent
        //val rezID: Int? = intent.getIntExtra("RezID")
        val rezIme = intent.getStringExtra("rezIme")

        //obj_ime.text = rezIme

        setTitle(rezIme)
        btn_save_rez.setOnClickListener(){
            if(edit_ime_rez.text.isEmpty() && datum_dol.text.isEmpty() && datum_odl.text.isEmpty()){
                Toast.makeText(this, "Ispunite sva polja", Toast.LENGTH_LONG).show()
                if(edit_ime_rez.text.isEmpty()) edit_ime_rez.requestFocus()
                else if(datum_dol.text.isEmpty()) datum_dol.requestFocus()
                else datum_odl.requestFocus()
            }else{
                val rez = Rezervacije()
                rez.rez_naziv = edit_ime_rez.text.toString()
                rez.dol_datum = datum_dol.text.toString()
                rez.odl_datum = datum_odl.text.toString()
                rez.app_naziv = obj_ime.text.toString()

                MainActivity.dbHandler.addRez(this, rez)
                ClearEdits()
            }
        }

        btn_cancel_rez.setOnClickListener(){
            ClearEdits()
            finish()
        }
    }
    private fun ClearEdits(){
        edit_ime_rez.text.clear()
        datum_dol.text.clear()
        datum_odl.text.clear()

    }
}

package com.example.apapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_objekat.*

class AddObjekat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_objekat)

        btn_save_rez.setOnClickListener(){
            if(txtNoviObjekt.text.isEmpty()){
                Toast.makeText(this, "Unesite ime objekta!", Toast.LENGTH_SHORT).show()
                txtNoviObjekt.requestFocus()
            }else{
                val objekat = Iznobjekti()
                objekat.izn_ime = txtNoviObjekt.text.toString()

                MainActivity.dbHandler.addObjekat(this, objekat)
                ClearEdits()
                txtNoviObjekt.requestFocus()
            }

        }

        btn_Cancel.setOnClickListener(){
            ClearEdits()
            finish()
        }
    }


    private fun ClearEdits(){
        txtNoviObjekt.text.clear()
    }
}

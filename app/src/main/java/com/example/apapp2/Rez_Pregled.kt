package com.example.apapp2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_rez__pregled.*

class RezPregled : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rez__pregled)

        val intent = intent
        //val rezID: Int? = intent.getIntExtra("RezID")
        val rezIme = intent.getStringExtra("rezIme")

        setTitle(rezIme)

        val rezLista = MainActivity.dbHandler.getRez(this, rezIme)
        val adapter = RezAdapter(this, rezLista)
        val rv1 : RecyclerView = findViewById(R.id.rv1)
        rv1.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager
        rv1.adapter = adapter

        fab_add.setOnClickListener(){
            val i = Intent(this, AddRez::class.java)
            intent.putExtra("rezIme", rezIme)
            startActivity(i)
        }
        floatie.setOnClickListener(){
            viewRezervacije()
        }




    }
    @SuppressLint("WrongConstant")
    private fun viewRezervacije() {
        val rezLista = MainActivity.dbHandler.getRezSve(this)
        val adapter = RezAdapter(this, rezLista)
        val rv1 : RecyclerView = findViewById(R.id.rv1)
        rv1.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager
        rv1.adapter = adapter
    }

    override fun onResume(){
        viewRezervacije()
        super.onResume()
    }
}

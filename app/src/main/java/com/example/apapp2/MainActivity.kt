package com.example.apapp2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var dbHandler: DBHandler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = DBHandler(this, null, null, 1)

        viewObjektovi()

        fab_add.setOnClickListener(){
            val i = Intent(this, AddObjekat::class.java)
            startActivity(i)
        }
    }

    @SuppressLint("WrongConstant")
    private fun viewObjektovi(){
        val objektilista = dbHandler.getObjekti(this)
        val adapter = IznajmAdapter(this, objektilista)
        val rv : RecyclerView = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager
        rv.adapter = adapter
    }

    override fun onResume(){
        viewObjektovi()
        super.onResume()
    }
}

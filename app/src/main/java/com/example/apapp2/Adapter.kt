package com.example.apapp2

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.objekat_edit.view.*
import kotlinx.android.synthetic.main.prikaz_izn.view.*

class IznajmAdapter(mCtx: Context, val iznajApp: ArrayList<Iznobjekti>): RecyclerView.Adapter<IznajmAdapter.ViewHolder>(){

    val mCtx = mCtx

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtObjIme = itemView.objIme
        val btnUpdate = itemView.btn_update
        val btnDelete = itemView.btn_delete
        val btnSelect = itemView.btn_select
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IznajmAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.prikaz_izn,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return iznajApp.size
    }

    @SuppressLint("InflateParams")
    override fun onBindViewHolder(holder: IznajmAdapter.ViewHolder, position: Int) {
        val iznajmljiv : Iznobjekti = iznajApp[position]
        holder.txtObjIme.text = iznajmljiv.izn_ime

        holder.btnDelete.setOnClickListener(){
            val objektIme = iznajmljiv.izn_ime

            var alertDialog = AlertDialog.Builder(mCtx)
                .setTitle("Upozorenje")
                .setMessage("Jeste li sigurni da biste brisali objekat $objektIme?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener{ dialog, which ->
                    if (MainActivity.dbHandler.deleteObjekat(iznajmljiv.izn_id)){
                        iznajApp.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, iznajApp.size)
                        Toast.makeText(mCtx, "Obrisan je objekat $objektIme", Toast.LENGTH_SHORT).show()
                    }else
                        Toast.makeText(mCtx, "Greska u brisanju objekta $objektIme", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which -> })
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()
        }

        holder.btnUpdate.setOnClickListener(){
            val inflater = LayoutInflater.from(mCtx)
            val view = inflater.inflate(R.layout.objekat_edit, null)

            //val txtObjekatIme : TextView = view.findViewById(R.id.edit_objektIme)
            val txtObjekatIme2 : TextView = view.findViewById(R.id.stari_naziv)

            txtObjekatIme2.text = iznajmljiv.izn_ime

            val builder = AlertDialog.Builder(mCtx)
                .setTitle("Uredi Objekat")
                .setView(view)
                .setPositiveButton("Uredi", DialogInterface.OnClickListener{dialog, which ->
                    val isUpdate = MainActivity.dbHandler.editObjekat(iznajmljiv.izn_id.toString(),
                        view.edit_objektIme.text.toString())
                    if(isUpdate == true){
                        iznajApp[position].izn_ime = view.edit_objektIme.text.toString()
                        notifyDataSetChanged()
                        Toast.makeText(mCtx, "Uspjesno editirano", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mCtx, "Greska u editiranju", Toast.LENGTH_SHORT).show()
                    }
                })
                .setNegativeButton("Odustani", DialogInterface.OnClickListener{dialog, which ->})
            val alert = builder.create()
            alert.show()
        }

        holder.btnSelect.setOnClickListener(){
            val prenosi_ime = iznajmljiv.izn_ime
            val prenosi_id = iznajmljiv.izn_id

            val intent = Intent(mCtx, RezPregled::class.java)
            intent.putExtra("RezID", prenosi_id)
            val putExtra = intent.putExtra("rezIme", prenosi_ime)
            mCtx.startActivity(intent)
        }
    }

}
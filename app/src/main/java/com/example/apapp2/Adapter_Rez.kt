package com.example.apapp2

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.novi_prikaz_rez.view.*
import kotlinx.android.synthetic.main.rez_edit_layout.view.*

//class IznajmAdapter(mCtx: Context, val iznajApp: ArrayList<Izn_objekti>): RecyclerView.Adapter<IznajmAdapter.ViewHolder>()

class RezAdapter(mCtx: Context, val rezerv: ArrayList<Rezervacije>): RecyclerView.Adapter<RezAdapter.ViewHolder>(){

    val mCtx = mCtx

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtRezNaziv = itemView.txtrez_naziv
        val txtDatum = itemView.txtdatum

        val rezDelete = itemView.rez_delete
        val rezEdit = itemView.rez_edit
        val rezSelect = itemView.rez_select
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RezAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.novi_prikaz_rez, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return rezerv.size
    }

    @SuppressLint("InflateParams")
    override fun onBindViewHolder(holder: RezAdapter.ViewHolder, position: Int) {
        /*
        val iznajmljiv : Izn_objekti = iznajApp[position]
        holder.txtObjIme.text = iznajmljiv.izn_ime
         */
        val rez : Rezervacije = rezerv[position]
        holder.txtRezNaziv.text = rez.rez_naziv
        holder.txtDatum.text = "${rez.dol_datum} - ${rez.odl_datum}"

        /*
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
         */
        holder.rezDelete.setOnClickListener(){
            val rezIme = rez.rez_naziv

            var alertDialog = AlertDialog.Builder(mCtx)
                .setTitle("Upozorenje")
                .setMessage("Jeste li sigurni da zelite obrisati $rezIme?")
                .setPositiveButton("Da", DialogInterface.OnClickListener{ dialog, which ->
                  if(MainActivity.dbHandler.deleteRez(rez.app_naziv)) {
                      rezerv.removeAt(position)
                      notifyItemRemoved(position)
                      notifyItemRangeChanged(position, rezerv.size)
                      Toast.makeText(mCtx, "Obrisana je rezervacija $rezIme", Toast.LENGTH_SHORT).show()
                  }else
                      Toast.makeText(mCtx, "Greska u brisanju rezervacije $rezIme", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("Ne", DialogInterface.OnClickListener{ dialog, which ->})
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()
        }

        /*
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
         */
        holder.rezEdit.setOnClickListener(){
            val inflater = LayoutInflater.from(mCtx)
            val view = inflater.inflate(R.layout.rez_edit_layout, null)

            val imence : TextView = view.findViewById(R.id.edit_rez_novo)
            val dol : TextView = view.findViewById(R.id.edit_dol_novo)
            val odl : TextView = view.findViewById(R.id.edit_odl_novo)

            val builder = AlertDialog.Builder(mCtx)
                .setTitle("Uredi Rezervaciju")
                .setView(view)
                .setPositiveButton("Uredi", DialogInterface.OnClickListener{dialog, which ->
                    val isUpdate = MainActivity.dbHandler.editRez(rez.rez_id.toString(), view.edit_rez_novo.toString(),
                        view.edit_dol_novo.toString(), view.edit_odl_novo.toString())
                    if(isUpdate == true){
                        rezerv[position].rez_naziv = view.edit_rez_novo.toString()
                        notifyDataSetChanged()
                        Toast.makeText(mCtx, "Uspjesno editirano", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mCtx, "Uspjesno editirano", Toast.LENGTH_SHORT).show()
                    }
                })
                .setNegativeButton("Odustani", DialogInterface.OnClickListener{dialog, which ->})
            val alert = builder.create()
            alert.show()
        }
    }

}
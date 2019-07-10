package com.example.apapp2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import kotlin.coroutines.coroutineContext

class DBHandler(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int):
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){

    companion object{
        private val DATABASE_NAME = "Apapp.db"
        private val DATABASE_VERSION = 1

        val IZN_TABLE_NAME = "Objekti"
        val COLUMN_IZN_ID = "iznajmid"
        val COLUMN_IZN_IME = "iznajmime"

        val REZ_TABLE_NAME = "Rezervacije"
        val COLUMN_REZ_ID = "rezid"
        val COLUMN_APP_NAME = "appid"
        val COLUMN_REZ_NAME = "rezime"
        val COLUMN_DATE_CO = "dolazak"
        val COLUMN_DATE_LE = "odlazak"

        val CREATE_IZN_TABLE = ("CREATE TABLE $IZN_TABLE_NAME ($COLUMN_IZN_ID INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", $COLUMN_IZN_IME TEXT)")
        val CREATE_REZ_TABLE = ("CREATE TABLE $REZ_TABLE_NAME ($COLUMN_REZ_ID INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", $COLUMN_APP_NAME TEXT, $COLUMN_REZ_NAME TEXT, $COLUMN_DATE_CO TEXT, $COLUMN_DATE_LE TEXT)")
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(CREATE_IZN_TABLE)
        try {
            p0?.execSQL(CREATE_REZ_TABLE)
        }catch (e: Exception){
            Log.e(ContentValues.TAG, "Nije kreirana tablica rezervacije")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getObjekti(mCtx: Context) : ArrayList<Iznobjekti>{
        val qry = "SELECT * FROM $IZN_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val objektovi = ArrayList<Iznobjekti>()

        if (cursor.count == 0)
            Toast.makeText(mCtx, "Nema nicega", Toast.LENGTH_SHORT).show()
        else{
            cursor.moveToFirst()

            while(!cursor.isAfterLast()){
                val objekat = Iznobjekti()
                objekat.izn_id = cursor.getInt(cursor.getColumnIndex(COLUMN_IZN_ID))
                objekat.izn_ime = cursor.getString(cursor.getColumnIndex(COLUMN_IZN_IME))
                objektovi.add(objekat)
                cursor.moveToNext()
            }
            Toast.makeText(mCtx, "${cursor.count.toString()} objekata nadjeno", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return objektovi
    }

    fun getRez(mCtx: Context, appIme: String) : ArrayList<Rezervacije>{
        val qry = "SELECT * FROM $REZ_TABLE_NAME WHERE $COLUMN_APP_NAME = ?"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(qry, arrayOf(appIme))
        }catch(e: SQLiteException){
            db.execSQL(CREATE_REZ_TABLE)
            return ArrayList()
        }
        val rezervacije = ArrayList<Rezervacije>()

        if(cursor.count == 0) Toast.makeText(mCtx, "Prazna lista", Toast.LENGTH_SHORT).show()
        else{
            cursor.moveToFirst()

            while(!cursor.isAfterLast()){
                val reze = Rezervacije()
                reze.rez_id = cursor.getInt(cursor.getColumnIndex(COLUMN_REZ_ID))
                reze.rez_naziv = cursor.getString(cursor.getColumnIndex(COLUMN_REZ_NAME))
                reze.app_naziv = cursor.getString(cursor.getColumnIndex(COLUMN_APP_NAME))
                reze.dol_datum = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_CO))
                reze.odl_datum = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_LE))
                rezervacije.add(reze)
                cursor.moveToNext()
            }
            Toast.makeText(mCtx, "${cursor.count.toString()} rezervacija pronadjeno", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return rezervacije
        }

    fun getRezSve(mCtx: Context) : ArrayList<Rezervacije>{
        val qry = "SELECT * FROM $REZ_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val rezervacije = ArrayList<Rezervacije>()

        if(cursor.count == 0) Toast.makeText(mCtx, "Prazna lista", Toast.LENGTH_SHORT).show()
        else{
            cursor.moveToFirst()

            while(!cursor.isAfterLast()){
                val reze = Rezervacije()
                reze.rez_id = cursor.getInt(cursor.getColumnIndex(COLUMN_REZ_ID))
                reze.rez_naziv = cursor.getString(cursor.getColumnIndex(COLUMN_REZ_NAME))
                reze.app_naziv = cursor.getString(cursor.getColumnIndex(COLUMN_APP_NAME))
                reze.dol_datum = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_CO))
                reze.odl_datum = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_LE))
                rezervacije.add(reze)
                cursor.moveToNext()
            }
            Toast.makeText(mCtx, "${cursor.count.toString()} rezervacija pronadjeno", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return rezervacije
    }

    fun addObjekat(mCtx: Context, objekat: Iznobjekti){
        val values = ContentValues()
        values.put(COLUMN_IZN_IME, objekat.izn_ime)

        val db = this.writableDatabase
        try {
            db.insert(IZN_TABLE_NAME, null, values)
            Toast.makeText(mCtx, "Uspjesno dodan objekat ${objekat.izn_ime}", Toast.LENGTH_SHORT).show()
        }catch(e : Exception){
            Toast.makeText(mCtx, "Desio se belaj", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun addRez(mCtx: Context, rez: Rezervacije){
        val values = ContentValues()
        values.put(COLUMN_REZ_NAME, rez.rez_naziv)
        values.put(COLUMN_APP_NAME, rez.app_naziv)
        values.put(COLUMN_DATE_CO, rez.dol_datum)
        values.put(COLUMN_DATE_LE, rez.odl_datum)

        val db = this.writableDatabase

        try {
            db.insert(REZ_TABLE_NAME, null, values)
            Toast.makeText(mCtx, "Uspjesno dodana rezervacija ${rez.rez_naziv}", Toast.LENGTH_SHORT).show()
        }catch(e: Exception){
            Toast.makeText(mCtx, "Greska u dodavanju", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteObjekat(iznjID : Int): Boolean {
        val qry = "DELETE FROM $IZN_TABLE_NAME WHERE $COLUMN_IZN_ID = $iznjID"
        val db = this.writableDatabase
        var result : Boolean = false

        try {
            //val cursor = db.delete(IZN_TABLE_NAME, "$COLUMN_IZN_ID = ?", arrayOf(iznjID.toString()))
            val cursor = db.execSQL(qry)
            result = true
        }catch(e: Exception){
            Log.e(ContentValues.TAG, "Greska u brisanju")
        }
        db.close()
        return result
    }

    fun deleteRez(rezName: String): Boolean {
        val qry = "DELETE FROM $REZ_TABLE_NAME WHERE $COLUMN_APP_NAME = '$rezName'"
        val db = this.writableDatabase
        var result: Boolean = false

        try {
            val cursor = db.execSQL(qry)
            result = true
        }catch (e: Exception){
            Log.e(ContentValues.TAG, "Greska u brisanju")
        }
        db.close()
        return result
    }

    fun editObjekat(id: String, objektIme : String) : Boolean {
        var result = false
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_IZN_IME, objektIme)
        try {
            db.update(IZN_TABLE_NAME, contentValues, "$COLUMN_IZN_ID = ?", arrayOf(id))
            result = true
        }catch (e: Exception){
            Log.e(ContentValues.TAG, "Greska u editiranju")
            result = false
        }
        return result
    }

    fun editRez(id: String, rezIme : String, datumDol: String, datumOdl: String) : Boolean{
        var result = false
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_REZ_NAME, rezIme)
        contentValues.put(COLUMN_DATE_CO, datumDol)
        contentValues.put(COLUMN_DATE_LE, datumOdl)
        try {
            db.update(REZ_TABLE_NAME, contentValues, "$COLUMN_REZ_ID = ?", arrayOf(id))
            result = true
        }catch(e: Exception){
            Log.e(ContentValues.TAG, "Greska u editiranju")
            result = false
        }
        return result
    }
}
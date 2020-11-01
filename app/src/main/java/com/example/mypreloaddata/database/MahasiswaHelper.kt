package com.example.mypreloaddata.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.example.mypreloaddata.database.DatabaseContract.MahasiswaColumns.Companion.NAMA
import com.example.mypreloaddata.database.DatabaseContract.MahasiswaColumns.Companion.NIM
import com.example.mypreloaddata.database.DatabaseContract.TABLE_NAME
import com.example.mypreloaddata.model.MahasiswaModel

class MahasiswaHelper(context: Context) {

    private val dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    @Throws(SQLiteException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun getAllData(): ArrayList<MahasiswaModel> {
        val cursor = database.query(TABLE_NAME, null, null, null, null, null, "$_ID ASC", null)
        cursor.moveToFirst()
        val arrayList = ArrayList<MahasiswaModel>()
        var mahasiswaModel: MahasiswaModel
        if (cursor.count > 0) {
            do {
                mahasiswaModel = MahasiswaModel()
                mahasiswaModel.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                mahasiswaModel.nama = cursor.getString(cursor.getColumnIndexOrThrow(NAMA))
                mahasiswaModel.nim = cursor.getString(cursor.getColumnIndexOrThrow(NIM))

                arrayList.add(mahasiswaModel)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    fun insert(mahasiswaModel: MahasiswaModel): Long {
        val initialValues = ContentValues()
        initialValues.put(NAMA, mahasiswaModel.nama)
        initialValues.put(NIM, mahasiswaModel.nim)
        return database.insert(TABLE_NAME, null, initialValues)
    }

    fun getDataByNama(nama: String): ArrayList<MahasiswaModel> {
        val cursor = database.query(TABLE_NAME, null, "$NAMA LIKE ?", arrayOf(nama), null, null, "$_ID ASC", null)
        cursor.moveToFirst()
        val arrayList = ArrayList<MahasiswaModel>()
        var mahasiswaModel: MahasiswaModel
        if (cursor.count > 0) {
            do {
                mahasiswaModel = MahasiswaModel()
                mahasiswaModel.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                mahasiswaModel.nama = cursor.getString(cursor.getColumnIndexOrThrow(NAMA))
                mahasiswaModel.nim = cursor.getString(cursor.getColumnIndexOrThrow(NIM))

                arrayList.add(mahasiswaModel)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    companion object {
        private var INSTANCE: MahasiswaHelper? = null

        fun getInstance(context: Context): MahasiswaHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MahasiswaHelper(context)
                    }
                }
            }
            return INSTANCE as MahasiswaHelper
        }
    }

}
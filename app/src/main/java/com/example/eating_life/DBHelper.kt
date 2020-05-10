package com.example.eating_life

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.ListView

class DBHelper(context: Context): SQLiteOpenHelper(context, "menu", null, 1){

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(
                "create table menu"
                        + "("
                        + " name text PRIMARY KEY, "
                        + " isSelect integer "
                        + "); "
            )
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun insertRecord(name: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("name", name)
        values.put("isSelect", 1)
        db.insert("menu", null, values)
    }

    fun deleteRecord(name: String) {
        val db = this.writableDatabase
        db?.execSQL("delete from menu where name = '$name';")
    }

    fun selectAll(): ArrayList<menu_item> {
        val db = readableDatabase
        val array = arrayListOf<menu_item>()
        if (db != null) {
            val c1 = db.rawQuery(
                "select * from menu;"
                , null
            )
            if(c1.moveToFirst())
            {
                do {
                    val name = c1.getString(0)
                    val isSelect = c1.getInt(1)
                    array.add(menu_item(name, isSelect))
                } while (c1.moveToNext())
            }
            c1.close()
        } else {
            println("db is null")
        }
        return (array)
    }

    fun select(name: String): ArrayList<menu_item> {
        val db = readableDatabase
        val array = arrayListOf<menu_item>()
        if (name == null || name == "")
            return selectAll()
        if (db != null) {
            val c1 = db.rawQuery(
                "select * from menu where name = '$name';"
                , null
            )
            if (c1 != null)
            {
                if(c1.moveToFirst())
                {
                    do {
                        val name = c1.getString(0)
                        val isSelect = c1.getInt(1)
                        array.add(menu_item(name, isSelect))
                    } while (c1.moveToNext())
                }
                c1.close()
            }
            else println("table is null")
        } else {
            println("db is null")
        }
        return (array)
    }

    fun search(name: String): ArrayList<menu_item> {
        val db = readableDatabase
        val array = arrayListOf<menu_item>()
        if (name == null || name == "")
            return selectAll()
        if (db != null) {
            val c1 = db.rawQuery(
                "select * from menu where name like '%$name%';"
                , null
            )
            if (c1 != null)
            {
                if(c1.moveToFirst())
                {
                    do {
                        val name = c1.getString(0)
                        val isSelect = c1.getInt(1)
                        array.add(menu_item(name, isSelect))
                    } while (c1.moveToNext())
                }
                c1.close()
            }
            else println("table is null")
        } else {
            println("db is null")
        }
        return (array)
    }
}
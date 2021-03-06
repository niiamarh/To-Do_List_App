package com.example.to_dolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManager {
    val dbName="MyList"
    val dbTable="List"
    private val colID="ID"
    private val colFirstName="FName"
    private val colLastName="LName"
    private val colNumber="PhoneNumber"
    val dbVersion=1

    //Create Database Table if not exist (ID,FIRSTNAME,LASTNAME,PHONENUMBER)
    val sqlCreateTable=
        "CREATE TABLE IF NOT EXISTS $dbTable($colID INTEGER PRIMARY KEY AUTOINCREMENT,$colFirstName TEXT,$colLastName TEXT,$colNumber  TEXT)"
    var sqlDB:SQLiteDatabase?=null

    constructor(context:Context){
        val db=DatabaseHelperList(context)
            sqlDB=db.writableDatabase
    }

    inner class DatabaseHelperList:SQLiteOpenHelper{
        var context:Context?=null
        constructor(context:Context):super(context,dbName,null,dbVersion){
            this.context=context
        }

        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context,"database is created", Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            p0!!.execSQL("Drop table IF EXISTS $dbTable")
        }
    }

    //Insert Values Into the Database Table
    fun Insert(values:ContentValues):Long{
        val ID= sqlDB!!.insert(dbTable,"",values)
        return ID
    }

    //Retrieves data from the Database Table
    fun Query(projection: Array<String>,selection:String,selectionArgs:Array<String>,sorOrder:String): Cursor {
        val qb= SQLiteQueryBuilder()
        qb.tables=dbTable
        val cursor=qb.query(sqlDB,projection,selection,selectionArgs,null,null,sorOrder)
        return cursor
    }

    //Deletes Values Into the Database Table
    fun Delete(selection:String,selectionArgs:Array<String>):Int{

        val count=sqlDB!!.delete(dbTable,selection,selectionArgs)
        return  count
    }

    //Updates Values Into the Database Table
    fun Update(values:ContentValues,selection:String,selectionargs:Array<String>):Int{

        val count=sqlDB!!.update(dbTable,values,selection,selectionargs)
        return count
    }
}
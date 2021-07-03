package com.example.to_dolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.ticket.view.*

class HomeActivity : AppCompatActivity() {

    private var backPressedTime:Long = 0

    var list=ArrayList<Todo_list>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //add dummy data
        list.add(Todo_list(1,"Raphael","Ashai","0548969376"))
        list.add(Todo_list(2,"Christiana","Ashai","0277076826"))
        list.add(Todo_list(3,"Sarah","Ashai","0247933086"))

        var myListAdapter=MyListAdapter(list)
        cardview.adapter=myListAdapter

        val BtnAdd = findViewById<ImageView>(R.id.BtnAdd)

        BtnAdd.setOnClickListener {
            val intent = Intent(this, AddList::class.java)
            startActivity(intent)
        }


    }

    /*fun LoadQuery(FName:String){

        var dbManager=DbManager(this)
        val projections=arrayOf("ID","FName","PhoneNumber")
        val selectionArgs= arrayOf(FName)
        val cursor=dbManager.Query(projections,"FName like ?",selectionArgs,"FName")
        list.clear()
        if(cursor.moveToFirst()){
            do{

                val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                val FName=cursor.getString(cursor.getColumnIndex("FName"))
                val PhoneNumber=cursor.getString(cursor.getColumnIndex("PhoneNumber"))

                list.add(Todo_list(ID,FName,PhoneNumber))
            }while (cursor.moveToNext())
        }

    }*/

    inner class MyListAdapter:BaseAdapter{
        var listAdapter=ArrayList<Todo_list>()
        constructor(listAdapter:ArrayList<Todo_list>):super(){
            this.listAdapter=listAdapter
        }

        override fun getCount(): Int {
            return listAdapter.size
        }

        override fun getItem(position: Int): Any {
            return listAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView=layoutInflater.inflate(R.layout.ticket,null)
            var myList=listAdapter[position]
            myView.textFName.text=myList.Todo_listFName
            myView.textLName.text=myList.Todo_listLName
            myView.textPhoneNo.text=myList.Todo_listNumber

            return myView
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            finish()
        }else{
            Toast.makeText(this, "Press back again to exit app", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}
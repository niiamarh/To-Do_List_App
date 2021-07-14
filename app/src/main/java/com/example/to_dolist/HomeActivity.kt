package com.example.to_dolist

import android.content.Context
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

        //add dummy data info
        //list.add(Todo_list(1,"Raphael","Ashai","0548969376"))
        //list.add(Todo_list(2,"Christiana","Ashai","0277076826"))
        //list.add(Todo_list(3,"Sarah","Ashai","0247933086"))

        //Redirect you to the add_list activity when the add icon is pressed
        val BtnAdd = findViewById<ImageView>(R.id.BtnAdd)

        BtnAdd.setOnClickListener {
            val intent = Intent(this, AddList::class.java)
            startActivity(intent)
        }
        //Toast.makeText(this,"onCreate",Toast.LENGTH_LONG).show()

        //Load from DB
        LoadQuery("%")

    }

    override  fun onResume() {
        super.onResume()
        LoadQuery("%")
        //Toast.makeText(this,"onResume",Toast.LENGTH_LONG).show()
    }


    //Retrieving all the data from the database and sorting the order by the firstname
    fun LoadQuery(fname:String){

        var dbManager=DbManager(this)
        val projection=arrayOf("ID","FName","LName","PhoneNumber")
        val selectionArgs= arrayOf(fname)
        val cursor=dbManager.Query(projection,"FName like ?",selectionArgs,"FName")
        list.clear()
        if(cursor.moveToFirst()){
            do{

                val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                val FName=cursor.getString(cursor.getColumnIndex("FName"))
                val LName=cursor.getString(cursor.getColumnIndex("LName"))
                val PhoneNumber=cursor.getString(cursor.getColumnIndex("PhoneNumber"))

                list.add(Todo_list(ID,FName,LName,PhoneNumber))
            }while (cursor.moveToNext())
        }
        var myListAdapter=MyListAdapter(this, list)
        cardview.adapter=myListAdapter

    }

    inner class MyListAdapter:BaseAdapter{
        var listAdapter=ArrayList<Todo_list>()
        var context: Context?=null
        constructor(context:Context, listAdapter:ArrayList<Todo_list>):super(){
            this.listAdapter=listAdapter
            this.context=context
        }

        override fun getCount(): Int {
            return listAdapter.size
        }

        override fun getItem(p0: Int): Any {
            return listAdapter[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        //Populates the home activity with the data from the database using the specified ticket layout provided
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var myView=layoutInflater.inflate(R.layout.ticket,null)
            var myList=listAdapter[p0]
            myView.textFName.text=myList.Todo_listFName
            myView.textLName.text=myList.Todo_listLName
            myView.textPhoneNo.text=myList.Todo_listNumber

            //Enable you to delete any populated layout/data from the home activity when the delete icon is pressed
            myView.deletelist.setOnClickListener(View.OnClickListener {
                var dbManager=DbManager(this.context!!)
                val selectionArgs= arrayOf(myList.Todo_listID.toString())
                dbManager.Delete("ID=?",selectionArgs)
                LoadQuery("%")
            })

            //Enable you to update any populated layout/data from the home activity when the update/edit icon is pressed
            myView.editlist.setOnClickListener(View.OnClickListener {
                GoToUpdate(myList)
            })

            return myView
        }
    }

    //Redirects you to the add_list activity with the values of the previous data to update any data you want to change
    fun GoToUpdate(note:Todo_list){
        var intent=  Intent(this,AddList::class.java)
        intent.putExtra("ID",note.Todo_listID)
        intent.putExtra("FName",note.Todo_listFName)
        intent.putExtra("LName",note.Todo_listLName)
        intent.putExtra("PhoneNumber",note.Todo_listNumber)
        startActivity(intent)
    }

    //Closes the activity when you back press double times in less than 2 seconds
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
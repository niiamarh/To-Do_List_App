package com.example.to_dolist

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_list.*


class AddList : AppCompatActivity() {
    val dbTable="List"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list)

        /*try{
            var bundle:Bundle= intent.extras!!
            id=bundle.getInt("ID",0)
            if(id!=0) {
                FirstName.setText(bundle.getString("name") )
                PhoneNo.setText(bundle.getString("des") )

            }
        }catch (ex:Exception){}*/
    }

    fun buAdd(view:View){
        var dbManager=DbManager(this)

        var values= ContentValues()
        values.put("FName",FirstName.text.toString())
        values.put("LName",LastName.text.toString())
        values.put("PhoneNumber",PhoneNo.text.toString())

        val ID= dbManager.Insert(values)
        if(ID>0){
            Toast.makeText(this,"List is Added", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Failed To Add", Toast.LENGTH_LONG).show()
        }

    }
    override fun onBackPressed() {
        finish()
    }
}
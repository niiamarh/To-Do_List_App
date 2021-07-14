package com.example.to_dolist

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_list.*


class AddList : AppCompatActivity() {
    val dbTable="List"
    var id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list)

        try{
            var bundle:Bundle= intent.extras!!
            id=bundle.getInt("ID",0)
            if(id!=0) {
                FirstName.setText(bundle.getString("FName") )
                LastName.setText(bundle.getString("LName") )
                PhoneNo.setText(bundle.getString("PhoneNumber") )

            }
        }catch (ex:Exception){}
    }

    fun buAdd(view:View){
        var dbManager=DbManager(this)

        var values= ContentValues()
        values.put("FName",FirstName.text.toString())
        values.put("LName",LastName.text.toString())
        values.put("PhoneNumber",PhoneNo.text.toString())

        if(id==0) {
            val ID = dbManager.Insert(values)
            if (ID > 0) {
                Toast.makeText(this, " note is added", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, " cannot add note ", Toast.LENGTH_LONG).show()
            }
        }else{
            var selectionArs= arrayOf(id.toString())
            val ID = dbManager.Update(values,"ID=?",selectionArs)
            if (ID > 0) {
                Toast.makeText(this, " note is added", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, " cannot add note ", Toast.LENGTH_LONG).show()
            }
        }

    }
    override fun onBackPressed() {
        finish()
    }
}
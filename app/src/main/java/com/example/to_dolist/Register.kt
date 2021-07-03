package com.example.to_dolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth =  FirebaseAuth.getInstance()

        val btn_signup = findViewById<Button>(R.id.signup)
        val btn_login = findViewById<TextView>(R.id.textExistingUser)
        val editUsername = findViewById<EditText>(R.id.editUsername)
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val editCPassword = findViewById<EditText>(R.id.editCPassword)

        //Redirect the user to the login Screen if the Sign up button is clicked
        btn_signup.setOnClickListener {


            if(editUsername.text.trim().toString().isNotEmpty() || editEmail.text.trim().toString().isNotEmpty() || editPassword.text.trim().toString().isNotEmpty() || editCPassword.text.trim().toString().isNotEmpty()){

                createUser(editEmail.text.trim().toString(), editPassword.text.trim().toString())

            }else{
                Toast.makeText(this, "Input Required",Toast.LENGTH_LONG).show()
            }
        }

        //Redirect the user to the Login Screen if the "Already Have An Account" text is clicked
        btn_login.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    fun createUser(email:String, password:String){

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->

                if(task.isSuccessful){
                    Log.e("Task Message", "Successful ...");
                    val intent = Intent(this, Login::class.java);
                    startActivity(intent);
                }else{
                    Log.e("Task Message", "Failed ..."+task.exception);
                }
            }
    }

    override fun onBackPressed() {
        finish()
    }

    /*override fun onStart() {
        super.onStart()
        val user = auth.currentUser;

        if(user != null){
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent);
        }
    }*/
}
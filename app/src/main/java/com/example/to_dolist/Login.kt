package com.example.to_dolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class Login : AppCompatActivity() {
    private var backPressedTime:Long = 0
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth =  FirebaseAuth.getInstance();

        val btn_signin = findViewById<Button>(R.id.sign_in)
        val btn_register = findViewById<TextView>(R.id.textNewUser)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)

        //Redirect the user to the Home Activity Screen if the Sign In button is clicked
        btn_signin.setOnClickListener {
            if(editTextEmail.text.trim().toString().isNotEmpty() || editTextPassword.text.trim().toString().isNotEmpty()){
                signInUser(editTextEmail.text.trim().toString(), editTextPassword.text.trim().toString());
            }else{
                Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
            }
        }

        //Redirect the user to the Register Screen if the "Don't Have An Account" text is clicked
        btn_register.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        }
    fun signInUser(email:String, password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->

                if(task.isSuccessful){
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Error !!"+task.exception, Toast.LENGTH_LONG).show()
                }
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
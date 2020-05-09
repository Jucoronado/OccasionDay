package com.gicogu.occasionday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class Restablecer : AppCompatActivity() {

    private lateinit var recmail:EditText
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restablecer)
        recmail=findViewById(R.id.recmail)
        mAuth = FirebaseAuth.getInstance()
    }

    fun recuperar(vista: View){

        val mail= recmail.text.toString()

        if(!mail.isEmpty()){
            mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(this){
                task ->

                if(task.isSuccessful){
                    Toast.makeText(applicationContext, "El Enlace de recuperación se ha enviado a su correo", Toast.LENGTH_LONG).show()
                    val intent = Intent( this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(applicationContext, "Mo se pudo enviar a su correo", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(applicationContext, "El espacio del correo no puede quedar vacio", Toast.LENGTH_SHORT).show()
        }

    }

    fun Cancelar(vista: View){
        val intent = Intent( this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}

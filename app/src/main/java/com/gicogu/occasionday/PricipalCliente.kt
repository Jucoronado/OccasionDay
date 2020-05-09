package com.gicogu.occasionday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import com.google.firebase.auth.FirebaseAuth

class PricipalCliente : AppCompatActivity() {

   private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pricipal_cliente)

        mAuth= FirebaseAuth.getInstance()

    }

     fun cerrar(vista: View){

        val intent = Intent( this, MainActivity::class.java)
        startActivity(intent)
         mAuth.signOut()
        finish()
    }
}

package com.gicogu.occasionday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }


    fun registrousu(vista: View){
        val intent = Intent( this@MainActivity, Registrar::class.java)
        startActivity(intent)
    }

    fun restablecer(vista: View){

        val intent = Intent( this@MainActivity, Restablecer::class.java)
        startActivity(intent)

    }


}
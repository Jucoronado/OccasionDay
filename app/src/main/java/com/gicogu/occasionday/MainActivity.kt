package com.gicogu.occasionday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mailog: EditText
    private lateinit var passlog: EditText
    private lateinit var progressBar: ProgressBar
    //private lateinit var passlog: EditText
    private lateinit var mAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mailog= findViewById(R.id.mailog)
        passlog= findViewById(R.id.passlog)
        mAuth= FirebaseAuth.getInstance()

    }


    fun logeo(vista: View){
        val correousuario:String= mailog.text.toString()
        val passusuario:String= passlog.text.toString()
        val pass:String = passlog.text.toString()


      if(!correousuario.isEmpty() && !passusuario.isEmpty()){
           // progressBar.visibility= View.VISIBLE

          var grupo = findViewById(R.id.radiolog) as RadioGroup
          var a=""
          if(grupo.getCheckedRadioButtonId() == R.id.clienter){

              a = "Clientes"
          }else if (grupo.getCheckedRadioButtonId() == R.id.socior){
              a = "Socios"
          }


          if(pass.length >=6 ){
        mAuth.signInWithEmailAndPassword(correousuario,passusuario)
            .addOnCompleteListener(this){
            task ->
            if(task.isSuccessful){
                Toast.makeText(applicationContext, "Bienvenido", Toast.LENGTH_SHORT).show()
                accion(a)


            }else{
                Toast.makeText(applicationContext, "Contraseña o Usuario Incorrectos", Toast.LENGTH_SHORT).show()
            } }}else{Toast.makeText(applicationContext, "La contraseña debe tener más de 5 digitos", Toast.LENGTH_SHORT).show() }
} else{ Toast.makeText(applicationContext, "No ha llenado todos los campos", Toast.LENGTH_SHORT).show()}
    }

     fun accion(a:String){
         if(a=="Clientes"){
             val intent = Intent( this, PricipalCliente::class.java)
             startActivity(intent)
             finish()}else if(a=="Socios"){
             val intent = Intent( this, PrincipalSocio::class.java)
             startActivity(intent)
             finish()
         }

    }

    fun registrousu(vista: View){
        val intent = Intent( this@MainActivity, Registrar::class.java)
        startActivity(intent)
        finish()
    }

    fun restablecer(vista: View){

        val intent = Intent( this@MainActivity, Restablecer::class.java)
        startActivity(intent)
        finish()
    }


}
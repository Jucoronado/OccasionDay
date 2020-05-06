package com.gicogu.occasionday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registrar.view.*


class Registrar : AppCompatActivity() {
    private lateinit var getcorreo: EditText
    private lateinit var getnombre: EditText
    private lateinit var  getapellido: EditText
    private lateinit var getpass: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mreferencia: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
        getcorreo= findViewById(R.id.correoreg)
        getnombre= findViewById(R.id.nombreusu)
        getapellido= findViewById(R.id.apelusu)
        getpass= findViewById(R.id.clave)
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        mreferencia= mDatabase.reference.child("Usuarios")
    }



    fun registro(vista: View){
        val correo:String = getcorreo.text.toString()
        val nombre:String = getnombre.text.toString()
        val apellido:String = getapellido.text.toString()
        val pass:String = getpass.text.toString()

        var grupo = findViewById(R.id.grupo) as RadioGroup
        var a=""
        if(grupo.getCheckedRadioButtonId() == R.id.clienter){

            a = "Clientes"
        }else if (grupo.getCheckedRadioButtonId() == R.id.socior){
            a = "Socios"
        }else {Toast.makeText(applicationContext, "Que Diantres", Toast.LENGTH_SHORT).show()}




        if (!correo.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !pass.isEmpty())
        {
            if(pass.length >=6 ){

        /*        grupo.setOnCheckedChangeListener({ group, checkedId ->
                    val rb = findViewById<RadioButton>(checkedId)
                    var a=""
                    when (rb) {

                        rb.clienter -> fireusuario(correo, nombre, apellido ,pass,"Clientes")
                        rb.socior -> fireusuario(correo, nombre, apellido ,pass,"Socios")
                    } }   )*/
                fireusuario(correo, nombre, apellido ,pass,a)

            }else{
                Toast.makeText(applicationContext, "La contraseña debe tener más de 5 digitos", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(applicationContext, "Debe completar los campos", Toast.LENGTH_SHORT).show()
        }
    }


    private fun fireusuario(correo: String, nombre: String, apellido: String, pass:String, usuario:String){
        val usu=usuario
        mAuth.createUserWithEmailAndPassword(correo,pass).addOnCompleteListener(this){
                task1 ->

            val user:FirebaseUser?=mAuth.currentUser
            //mailverify(user)

            if(task1.isSuccessful){

                user?.sendEmailVerification()?.addOnCompleteListener(this){ task->
                    if(task.isComplete){
                        val userBD=mreferencia.child(usu).child(user?.uid.toString())
                        userBD.child("nombre").setValue(nombre)
                        userBD.child("apellido").setValue(apellido)
                        userBD.child("correo").setValue(correo)
                        Toast.makeText(applicationContext, "Correo registradoo", Toast.LENGTH_SHORT).show()
                        logIn()
                    }else{
                        Toast.makeText(applicationContext, "Correo No Registrado", Toast.LENGTH_SHORT).show()
                    }
                }


            }else {
                Toast.makeText(applicationContext, "El usuario ya esta registrado", Toast.LENGTH_SHORT).show()
            }
        }
    }

  /**  private fun mailverify(user:FirebaseUser?){

        user?.sendEmailVerification()?.addOnCompleteListener(this){ task->
            if(task.isComplete){
                Toast.makeText(applicationContext, "Correo Registrado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Correo no registradoo", Toast.LENGTH_SHORT).show()
            }
        }
    }*/

    private fun logIn(){
        Toast.makeText(applicationContext, "Gracias", Toast.LENGTH_SHORT).show()
        val intent = Intent( this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun cancelar(vista: View){

        val intent = Intent( this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
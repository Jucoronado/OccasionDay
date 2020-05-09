package com.gicogu.occasionday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
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
    private lateinit var progressBar: ProgressBar
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mreferencia: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
        progressBar=ProgressBar(this)
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

        if (!correo.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !pass.isEmpty())// se verifica que ningun espacio este vacio
        {
            if(pass.length >=6 ){ // firebase usa contraseñas con 6 o mas caracteres, se debe asegurar que lo sea
        /*        grupo.setOnCheckedChangeListener({ group, checkedId ->
                    val rb = findViewById<RadioButton>(checkedId)
                    var a=""
                    when (rb) {

                        rb.clienter -> fireusuario(correo, nombre, apellido ,pass,"Clientes")
                        rb.socior -> fireusuario(correo, nombre, apellido ,pass,"Socios")
                    } }   )*/
                fireusuario(correo, nombre, apellido ,pass,a)
            }else{ Toast.makeText(applicationContext, "La contraseña debe tener más de 5 digitos", Toast.LENGTH_SHORT).show()           }
        }else{  Toast.makeText(applicationContext, "Debe completar los campos", Toast.LENGTH_SHORT).show()         }
    }

    private fun fireusuario(correo: String, nombre: String, apellido: String, pass:String, usuario:String){
        val usu=usuario
        mAuth.createUserWithEmailAndPassword(correo,pass).addOnCompleteListener(this){ //Crea el usuario en la Base de datos, usando su email y su pass
                task1 ->

            val user:FirebaseUser?=mAuth.currentUser
            //mailverify(user)

            if(task1.isSuccessful){
                progressBar.visibility=View.VISIBLE
                user?.sendEmailVerification()?.addOnCompleteListener(this){ task-> // Verifica si hay un usuario ya registrado con email
                    if(task.isComplete){
                        val userBD=mreferencia.child(usu).child(user?.uid.toString())
                        userBD.child("nombre").setValue(nombre)
                        userBD.child("apellido").setValue(apellido)
                        userBD.child("correo").setValue(correo)
                        Toast.makeText(applicationContext, "Correo registradoo", Toast.LENGTH_SHORT).show()
                        mAuth.signOut()
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

    private fun logIn(){ // simplemente volver a la pagina anterior, se usa el metodo finish para inpedir que la aplicacion se devuelva
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
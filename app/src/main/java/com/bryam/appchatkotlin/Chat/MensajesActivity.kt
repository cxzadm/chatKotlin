package com.bryam.appchatkotlin.Chat

import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bryam.appchatkotlin.Modelo.Usuario
import com.bryam.appchatkotlin.R
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MensajesActivity : AppCompatActivity() {

    private lateinit var imagen_perfil_chat : ImageView
    private lateinit var N_usuario_chat : TextView
    private lateinit var Et_mensaje : EditText
    private lateinit var IB_Adjuntar : ImageButton
    private lateinit var IB_Enviar : ImageButton
    var uid_usuario_seleccionado : String = ""
    var firebaseUser : FirebaseUser?= null
    private var imagenUri : Uri?= null

    lateinit var RV_chats : RecyclerView


    var reference : DatabaseReference?= null
    var seenListener : ValueEventListener?= null

    var notificar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mensajes)
        InicializarVistas()
        ObtenerUid()
        LeerInfoUsuarioSeleccionado()

        IB_Enviar.setOnClickListener {
            notificar = true
            val mensaje = Et_mensaje.text.toString()
            if (mensaje.isEmpty()){
                Toast.makeText(applicationContext, "Por favor ingrese un mensaje", Toast.LENGTH_SHORT).show()
            }else{
                EnviarMensaje(firebaseUser!!.uid, uid_usuario_seleccionado, mensaje)
                Et_mensaje.setText("")
            }
        }
    }

    private fun EnviarMensaje(uid: String, uidUsuarioSeleccionado: String, mensaje: String) {

    }

    private fun ObtenerUid(){
        intent = intent
        uid_usuario_seleccionado = intent.getStringExtra("uid_usuario").toString()
    }
    private fun InicializarVistas(){

        val toolbar : Toolbar = findViewById(R.id.toolbar_chat)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""


        imagen_perfil_chat = findViewById(R.id.imagen_perfil_chat)
        N_usuario_chat = findViewById(R.id.N_usuario_chat)
        Et_mensaje = findViewById(R.id.Et_mensaje)
        IB_Adjuntar = findViewById(R.id.IB_Adjuntar)
        IB_Enviar = findViewById(R.id.IB_Enviar)
        firebaseUser = FirebaseAuth.getInstance().currentUser

        RV_chats = findViewById(R.id.RV_chats)
        RV_chats.setHasFixedSize(true)
        var linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true
        RV_chats.layoutManager = linearLayoutManager
    }

    private fun LeerInfoUsuarioSeleccionado(){
        val reference = FirebaseDatabase.getInstance().reference.child("Usuarios")
            .child(uid_usuario_seleccionado)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usuario : Usuario? = snapshot.getValue(Usuario::class.java)
                //Obtener el nombre de usuario
                N_usuario_chat.text = usuario!!.getN_Usuario()
                //Obtenemos la imagen de perfil
                Glide.with(applicationContext).load(usuario.getImagen())
                    .placeholder(R.drawable.ic_item_usuario)
                    .into(imagen_perfil_chat)

                //RecuperarMensajes(firebaseUser!!.uid, uid_usuario_seleccionado, usuario.getImagen())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
package com.bryam.appchatkotlin.Adaptador

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bryam.appchatkotlin.Modelo.Chat
import com.bryam.appchatkotlin.R
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase



class AdaptadorChat (contexto : Context, chatLista : List<Chat>, imagenUrl : String)
    : RecyclerView.Adapter<AdaptadorChat.ViewHolder?>(){

    private val contexto : Context
    private val chatLista : List<Chat>
    private val imagenUrl : String
    var firebaseUser : FirebaseUser = FirebaseAuth.getInstance().currentUser!!

    init {
        this.contexto = contexto
        this.chatLista = chatLista
        this.imagenUrl = imagenUrl
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        /*Vistas de item mensaje izquiedo*/
        var imagen_perfil_mensaje : ImageView?= null
        var TXT_ver_mensaje : TextView?= null
        var imagen_enviada_izquierdo : ImageView?= null
        var TXT_mensaje_visto : TextView?= null

        /*Vista de item mensaje derecho*/
        var imagen_enviada_derecha : ImageView?= null

        init {
            imagen_perfil_mensaje = itemView.findViewById(R.id.imagen_perfil_mensaje)
            TXT_ver_mensaje = itemView.findViewById(R.id.TXT_ver_mensaje)
            imagen_enviada_izquierdo = itemView.findViewById(R.id.imagen_enviada_izquierdo)
            TXT_mensaje_visto = itemView.findViewById(R.id.TXT_mensaje_visto)
            imagen_enviada_derecha = itemView.findViewById(R.id.imagen_enviada_derecha)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return if (position == 1){
            val view : View = LayoutInflater.from(contexto).inflate(com.bryam.appchatkotlin.R.layout.item_mensaje_derecho, parent, false)
            ViewHolder(view)
        }else{
            val view : View = LayoutInflater.from(contexto).inflate(com.bryam.appchatkotlin.R.layout.item_mensaje_izquierdo,parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat : Chat = chatLista[position]
        Glide.with(contexto).load(imagenUrl).placeholder(R.drawable.ic_imagen_chat).into(holder.imagen_perfil_mensaje!!)

        /*Si el mensaje contiene una imagen*/
        if (chat.getMensaje().equals("Se ha enviado la imagen") && !chat.getUrl().equals("")){
            /*Condición es para el usuario que envía una imagen como mensaje*/
            if (chat.getEmisor().equals(firebaseUser!!.uid)){
                holder.TXT_ver_mensaje!!.visibility = View.GONE
                holder.imagen_enviada_derecha!!.visibility = View.VISIBLE
                Glide.with(contexto).load(chat.getUrl()).placeholder(R.drawable.ic_imagen_enviada).into(holder.imagen_enviada_derecha!!)

                holder.imagen_enviada_derecha!!.setOnClickListener {
                    val opciones = arrayOf<CharSequence>("Ver imagen completa","Eliminar imagen", "Cancelar")
                    val builder : AlertDialog.Builder = AlertDialog.Builder(holder.itemView.context)
                    builder.setTitle("¿Qúe desea realizar?")
                    builder.setItems(opciones, DialogInterface.OnClickListener{
                            dialogInterface, i ->
                        if (i == 0){
                            //VisualizarImagen(chat.getUrl())
                        }
                        else if (i == 1){
                            EliminarMensaje(position, holder)
                        }
                    })
                    builder.show()
                }
            }
            /*Condición para el usuario el cuál nos envia una imagen como mensaje*/
            else if (!chat.getEmisor().equals(firebaseUser!!.uid)){
                holder.TXT_ver_mensaje!!.visibility = View.GONE
                holder.imagen_enviada_izquierdo!!.visibility = View.VISIBLE
                Glide.with(contexto).load(chat.getUrl()).placeholder(R.drawable.ic_imagen_enviada).into(holder.imagen_enviada_izquierdo!!)

                holder.imagen_enviada_izquierdo!!.setOnClickListener {
                    val opciones = arrayOf<CharSequence>("Ver imagen completa", "Cancelar")
                    val builder : AlertDialog.Builder = AlertDialog.Builder(holder.itemView.context)
                    builder.setTitle("¿Qúe desea realizar?")
                    builder.setItems(opciones, DialogInterface.OnClickListener{
                            dialogInterface, i ->
                        if (i == 0){
                            //VisualizarImagen(chat.getUrl())
                        }
                    })
                    builder.show()
                }

            }
        }
        /*Si el mensaje contiene sólo texto*/
        else{
            holder.TXT_ver_mensaje!!.text = chat.getMensaje()
            if (firebaseUser!!.uid == chat.getEmisor()){
                holder.TXT_ver_mensaje!!.setOnClickListener {
                    val opciones = arrayOf<CharSequence>("Eliminar mensaje", "Cancelar")
                    val builder : AlertDialog.Builder = AlertDialog.Builder(holder.itemView.context)
                    builder.setTitle("¿Qué desea realizar?")
                    builder.setItems(opciones, DialogInterface.OnClickListener {
                            dialogInterface, i ->
                        if (i == 0){
                            EliminarMensaje(position, holder)
                        }

                    })
                    builder.show()
                }
            }
        }

        /*Mensaje enviado y visto*/
        if (position == chatLista.size-1){
            if (chat.isVisto()){
                holder.TXT_mensaje_visto!!.text = "Visto"
                if (chat.getMensaje().equals("Se ha enviado la imagen") && !chat.getUrl().equals("")){
                    val lp : RelativeLayout.LayoutParams = holder.TXT_mensaje_visto!!.layoutParams as RelativeLayout.LayoutParams
                    lp!!.setMargins(0,245, 10,0)
                    holder.TXT_mensaje_visto!!.layoutParams = lp
                }
            }else{
                holder.TXT_mensaje_visto!!.text = "Enviado"
                if (chat.getMensaje().equals("Se ha enviado la imagen") && !chat.getUrl().equals("")){
                    val lp : RelativeLayout.LayoutParams = holder.TXT_mensaje_visto!!.layoutParams as RelativeLayout.LayoutParams
                    lp!!.setMargins(0,245, 10,0)
                    holder.TXT_mensaje_visto!!.layoutParams = lp
                }
            }
        }else{
            holder.TXT_mensaje_visto!!.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return chatLista.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatLista[position].getEmisor().equals(firebaseUser!!.uid)){
            1
        }else{
            0
        }
    }



    private fun EliminarMensaje(position: Int, holder : AdaptadorChat.ViewHolder){

        val reference = FirebaseDatabase.getInstance().reference.child("Chats")
            .child(chatLista.get(position).getId_Mensaje()!!)
            .removeValue()
            .addOnCompleteListener { tarea->
                if (tarea.isSuccessful){
                    Toast.makeText(holder.itemView.context, "Mensaje eliminado", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(holder.itemView.context, "No se ha eliminado el mensaje, intente nuevamente", Toast.LENGTH_SHORT).show()
                }
            }


    }

}
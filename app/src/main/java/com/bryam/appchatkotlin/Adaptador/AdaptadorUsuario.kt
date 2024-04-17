package com.bryam.appchatkotlin.Adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bryam.appchatkotlin.Modelo.Usuario
import com.bryam.appchatkotlin.R
import com.bumptech.glide.Glide

class AdaptadorUsuario (context : Context, listaUsuarios : List<Usuario>, chatLeido : Boolean) : RecyclerView.Adapter<AdaptadorUsuario.ViewHolder?>(){

    private val context : Context
    private val listaUsuarios : List<Usuario>
    private var chatLeido : Boolean
    var ultimoMensaje : String = ""

    init {
        this.context = context
        this.listaUsuarios = listaUsuarios
        this.chatLeido = chatLeido
    }

    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        var nombre_usuario : TextView
        //var email_usuario : TextView
        var imagen_usuario : ImageView
        var imagen_online : ImageView
        var imagen_offline : ImageView
        var Txt_ultimo_mensaje : TextView

        init {
            nombre_usuario = itemView.findViewById(R.id.Item_nombre_usuario)
            //email_usuario = itemView.findViewById(R.id.Item_email_usuario)
            imagen_usuario = itemView.findViewById(R.id.Item_imagen)
            imagen_online = itemView.findViewById(R.id.imagen_online)
            imagen_offline = itemView.findViewById(R.id.imagen_offline)
            Txt_ultimo_mensaje = itemView.findViewById(R.id.Txt_ultimo_mensaje)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario : Usuario = listaUsuarios[position]
        holder.nombre_usuario.text = usuario.getN_Usuario()
        //holder.email_usuario.text = usuario.getEmail()
        Glide.with(context).load(usuario.getImagen()).placeholder(R.drawable.ic_item_usuario).into(holder.imagen_usuario)
    }

    override fun getItemCount(): Int {
        return listaUsuarios.size
    }
}
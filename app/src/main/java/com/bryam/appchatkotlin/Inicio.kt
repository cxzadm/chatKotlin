package com.bryam.appchatkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Inicio : AppCompatActivity() {

    private lateinit var  Btn_ir_registros : Button
    private lateinit var  Btn_ir_logeo : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)

        Btn_ir_registros = findViewById(R.id.Btn_ir_registros)
        Btn_ir_logeo = findViewById(R.id.Btn_ir_logeo)

        Btn_ir_registros.setOnClickListener {
            val intent = Intent(this@Inicio, RegistroActivity::class.java)
            Toast.makeText(applicationContext, "Registros", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
        Btn_ir_logeo.setOnClickListener {
            val intent = Intent(this@Inicio, LoginActivity::class.java)
            Toast.makeText(applicationContext, "Login", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}
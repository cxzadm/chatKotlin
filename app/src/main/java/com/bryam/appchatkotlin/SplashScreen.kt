package com.bryam.appchatkotlin

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        mostrarBienvenida()
    }

    fun mostrarBienvenida() {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
                // En caso de que necesites hacer algo en cada tick del temporizador
            }

            override fun onFinish() {
                val intent = Intent(applicationContext, Inicio::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }
}

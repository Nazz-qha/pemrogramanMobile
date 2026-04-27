package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import android.widget.TextView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Ambil email dari Login
        val userEmail = intent.getStringExtra("USER_EMAIL") ?: "Mahasiswa"

        val tvWelcome = findViewById<TextView>(R.id.tvWelcomeUser)
        tvWelcome.text = "SELAMAT DATANG!\n$userEmail"

        val btnDaftarSeminar = findViewById<MaterialButton>(R.id.btnDaftarSeminar)
        btnDaftarSeminar.setOnClickListener {
            val intent = Intent(this, FormSeminarActivity::class.java)
            intent.putExtra("USER_EMAIL", userEmail)
            startActivity(intent)
        }
    }
}
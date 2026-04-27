package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class HasilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil)

        supportActionBar?.title = "Hasil Pendaftaran"

        // Ambil data dari intent
        val nama = intent.getStringExtra("NAMA") ?: "-"
        val email = intent.getStringExtra("EMAIL") ?: "-"
        val hp = intent.getStringExtra("HP") ?: "-"
        val jk = intent.getStringExtra("JK") ?: "-"
        val seminar = intent.getStringExtra("SEMINAR") ?: "-"

        // Tampilkan ke TextView
        findViewById<TextView>(R.id.tvHasilNama).text = nama
        findViewById<TextView>(R.id.tvHasilEmail).text = email
        findViewById<TextView>(R.id.tvHasilHp).text = hp
        findViewById<TextView>(R.id.tvHasilJK).text = jk
        findViewById<TextView>(R.id.tvHasilSeminar).text = seminar

        // Tombol kembali ke Home
        findViewById<MaterialButton>(R.id.btnKembaliHome).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // hapus stack Activity
            startActivity(intent)
            finish()
        }
    }
}
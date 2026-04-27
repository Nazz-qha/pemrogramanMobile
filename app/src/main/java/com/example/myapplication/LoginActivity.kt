package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnGoRegister: MaterialButton

    // Akun hardcode bawaan (akun demo)
    private val defaultEmail = "mahasiswa@utb.ac.id"
    private val defaultPassword = "password123"

    // Akun dari registrasi baru (awalnya kosong)
    private var registeredEmail: String = ""
    private var registeredPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Ambil data akun baru dari RegisterActivity (kalau ada)
        registeredEmail = intent.getStringExtra("REGISTERED_EMAIL") ?: ""
        registeredPassword = intent.getStringExtra("REGISTERED_PASSWORD") ?: ""

        // Auto-fill email & password dari registrasi
        // supaya user tinggal klik Login
        initViews()

        if (registeredEmail.isNotEmpty()) {
            etEmail.setText(registeredEmail)
            etPassword.setText(registeredPassword)
        }

        setupRealTimeValidation()
        setupButtons()
    }

    private fun initViews() {
        tilEmail = findViewById(R.id.tilLoginEmail)
        tilPassword = findViewById(R.id.tilLoginPassword)
        etEmail = findViewById(R.id.etLoginEmail)
        etPassword = findViewById(R.id.etLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnGoRegister = findViewById(R.id.btnGoRegister)
    }

    private fun setupRealTimeValidation() {
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString().trim()
                when {
                    email.isEmpty() -> tilEmail.error = "Email tidak boleh kosong"
                    !email.contains("@") -> tilEmail.error = "Format email tidak valid"
                    else -> { tilEmail.error = null; tilEmail.isErrorEnabled = false }
                }
            }
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val pass = s.toString()
                when {
                    pass.isEmpty() -> tilPassword.error = "Password tidak boleh kosong"
                    pass.length < 6 -> tilPassword.error = "Password minimal 6 karakter"
                    else -> { tilPassword.error = null; tilPassword.isErrorEnabled = false }
                }
            }
        })
    }

    private fun setupButtons() {
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (validateLogin(email, password)) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("USER_EMAIL", email)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }

        btnGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validateLogin(email: String, password: String): Boolean {
        var isValid = true

        // Cek apakah email & password cocok dengan:
        // 1. Akun demo bawaan, ATAU
        // 2. Akun yang baru saja didaftarkan
        val isDefaultAccount = (email == defaultEmail && password == defaultPassword)
        val isNewAccount = (registeredEmail.isNotEmpty()
                && email == registeredEmail
                && password == registeredPassword)

        if (email.isEmpty()) {
            tilEmail.error = "Email tidak boleh kosong"
            isValid = false
        } else if (!email.contains("@")) {
            tilEmail.error = "Format email tidak valid"
            isValid = false
        } else if (!isDefaultAccount && !isNewAccount) {
            tilEmail.error = "Email tidak terdaftar"
            isValid = false
        } else {
            tilEmail.error = null
            tilEmail.isErrorEnabled = false
        }

        if (password.isEmpty()) {
            tilPassword.error = "Password tidak boleh kosong"
            isValid = false
        } else if (!isDefaultAccount && !isNewAccount) {
            // Error password hanya tampil kalau email valid tapi password salah
        } else {
            tilPassword.error = null
            tilPassword.isErrorEnabled = false
        }

        return isValid
    }
}
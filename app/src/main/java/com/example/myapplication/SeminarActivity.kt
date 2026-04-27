package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormSeminarActivity : AppCompatActivity() {

    private lateinit var tilNama: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilHp: TextInputLayout
    private lateinit var etNama: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etHp: TextInputEditText
    private lateinit var rgJenisKelamin: RadioGroup
    private lateinit var tvJkError: TextView
    private lateinit var spinnerSeminar: Spinner
    private lateinit var tvSpinnerError: TextView
    private lateinit var cbSetuju: CheckBox
    private lateinit var tvCheckboxError: TextView
    private lateinit var btnSubmit: MaterialButton

    private val daftarSeminar = listOf(
        "-- Pilih Seminar --",
        "Seminar AI & Machine Learning",
        "Seminar Cybersecurity",
        "Seminar Mobile Development",
        "Seminar Cloud Computing",
        "Seminar UI/UX Design",
        "Seminar Data Science",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seminar)

        // Set judul toolbar
        supportActionBar?.title = "Form Pendaftaran Seminar"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        setupSpinner()
        setupRealTimeValidation()
        setupSubmitButton()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initViews() {
        tilNama = findViewById(R.id.tilSeminarNama)
        tilEmail = findViewById(R.id.tilSeminarEmail)
        tilHp = findViewById(R.id.tilSeminarHp)
        etNama = findViewById(R.id.etSeminarNama)
        etEmail = findViewById(R.id.etSeminarEmail)
        etHp = findViewById(R.id.etSeminarHp)
        rgJenisKelamin = findViewById(R.id.rgSeminarJK)
        tvJkError = findViewById(R.id.tvSeminarJKError)
        spinnerSeminar = findViewById(R.id.spinnerSeminar)
        tvSpinnerError = findViewById(R.id.tvSpinnerError)
        cbSetuju = findViewById(R.id.cbSetuju)
        tvCheckboxError = findViewById(R.id.tvCheckboxError)
        btnSubmit = findViewById(R.id.btnSeminarSubmit)
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, daftarSeminar)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSeminar.adapter = adapter

        spinnerSeminar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p: AdapterView<*>, v: View?, pos: Int, id: Long) {
                if (pos != 0) tvSpinnerError.visibility = View.GONE
            }
            override fun onNothingSelected(p: AdapterView<*>) {}
        }
    }

    private fun setupRealTimeValidation() {
        // Nama
        etNama.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val v = s.toString().trim()
                when {
                    v.isEmpty() -> tilNama.error = "Nama tidak boleh kosong"
                    v.length < 3 -> tilNama.error = "Nama minimal 3 karakter"
                    else -> { tilNama.error = null; tilNama.isErrorEnabled = false }
                }
            }
        })

        // Email
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val v = s.toString().trim()
                when {
                    v.isEmpty() -> tilEmail.error = "Email tidak boleh kosong"
                    !v.contains("@") -> tilEmail.error = "Email harus mengandung @"
                    else -> { tilEmail.error = null; tilEmail.isErrorEnabled = false }
                }
            }
        })

        // Nomor HP
        etHp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val v = s.toString().trim()
                when {
                    v.isEmpty() -> tilHp.error = "Nomor HP tidak boleh kosong"
                    !v.all { it.isDigit() } -> tilHp.error = "Hanya boleh angka"
                    v.length < 10 || v.length > 13 -> tilHp.error = "Panjang 10-13 digit"
                    !v.startsWith("08") -> tilHp.error = "Harus diawali dengan 08"
                    else -> { tilHp.error = null; tilHp.isErrorEnabled = false }
                }
            }
        })

        // Checkbox real-time
        cbSetuju.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) tvCheckboxError.visibility = View.GONE
        }

        // RadioGroup real-time
        rgJenisKelamin.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) tvJkError.visibility = View.GONE
        }
    }

    private fun setupSubmitButton() {
        btnSubmit.setOnClickListener {
            if (validateAll()) {
                showConfirmDialog()
            }
        }
    }

    private fun validateAll(): Boolean {
        var valid = true

        val nama = etNama.text.toString().trim()
        when {
            nama.isEmpty() -> { tilNama.error = "Nama tidak boleh kosong"; valid = false }
            nama.length < 3 -> { tilNama.error = "Nama minimal 3 karakter"; valid = false }
            else -> tilNama.error = null
        }

        val email = etEmail.text.toString().trim()
        when {
            email.isEmpty() -> { tilEmail.error = "Email tidak boleh kosong"; valid = false }
            !email.contains("@") -> { tilEmail.error = "Email harus mengandung @"; valid = false }
            else -> tilEmail.error = null
        }

        val hp = etHp.text.toString().trim()
        when {
            hp.isEmpty() -> { tilHp.error = "Nomor HP tidak boleh kosong"; valid = false }
            !hp.all { it.isDigit() } -> { tilHp.error = "Hanya boleh angka"; valid = false }
            hp.length < 10 || hp.length > 13 -> { tilHp.error = "Panjang 10-13 digit"; valid = false }
            !hp.startsWith("08") -> { tilHp.error = "Harus diawali 08"; valid = false }
            else -> tilHp.error = null
        }

        if (rgJenisKelamin.checkedRadioButtonId == -1) {
            tvJkError.text = "Pilih jenis kelamin!"
            tvJkError.visibility = View.VISIBLE
            valid = false
        } else {
            tvJkError.visibility = View.GONE
        }

        if (spinnerSeminar.selectedItemPosition == 0) {
            tvSpinnerError.text = "Pilih seminar!"
            tvSpinnerError.visibility = View.VISIBLE
            valid = false
        } else {
            tvSpinnerError.visibility = View.GONE
        }

        if (!cbSetuju.isChecked) {
            tvCheckboxError.text = "Kamu harus menyetujui pernyataan ini!"
            tvCheckboxError.visibility = View.VISIBLE
            valid = false
        } else {
            tvCheckboxError.visibility = View.GONE
        }

        return valid
    }

    private fun showConfirmDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Data")
            .setMessage("Apakah data yang Anda isi sudah benar?")
            .setPositiveButton("Ya, Lanjut") { dialog, _ ->
                dialog.dismiss()
                goToHasil()
            }
            .setNegativeButton("Periksa Lagi") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun goToHasil() {
        val jenisKelamin = if (rgJenisKelamin.checkedRadioButtonId == R.id.rbSeminarLaki)
            "Laki-laki" else "Perempuan"

        val intent = Intent(this, HasilActivity::class.java).apply {
            putExtra("NAMA", etNama.text.toString().trim())
            putExtra("EMAIL", etEmail.text.toString().trim())
            putExtra("HP", etHp.text.toString().trim())
            putExtra("JK", jenisKelamin)
            putExtra("SEMINAR", spinnerSeminar.selectedItem.toString())
        }
        startActivity(intent)
    }
}
package com.example.myapplication  // Sesuaikan dengan package name kamu!

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    // DECLARE SEMUA VIEW
    // TextInputLayout (untuk tampilkan error)
    private lateinit var tilNama: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilConfirmPassword: TextInputLayout

    // TextInputEditText (untuk ambil nilai)
    private lateinit var etNama: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText

    // Radio & Checkbox
    private lateinit var rgJenisKelamin: RadioGroup
    private lateinit var tvJenisKelaminError: TextView

    private lateinit var cbMembaca: CheckBox
    private lateinit var cbOlahraga: CheckBox
    private lateinit var cbMusik: CheckBox
    private lateinit var cbGaming: CheckBox
    private lateinit var cbMasak: CheckBox
    private lateinit var cbTravel: CheckBox
    private lateinit var tvHobiError: TextView

    // Spinner
    private lateinit var spinnerKota: Spinner
    private lateinit var tvKotaError: TextView

    // Buttons
    private lateinit var btnSubmit: Button
    private lateinit var btnReset: Button

    // ========== DATA SPINNER ==========
    private val daftarKota = listOf(
        "-- Pilih Kota --",
        "Bandung",
        "Jakarta",
        "Surabaya",
        "Yogyakarta",
        "Medan",
        "Makassar",
        "Semarang",
        "Palembang",
        "Bali"
    )

    // onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Inisialisasi semua view
        initViews()

        // 2. Setup spinner
        setupSpinner()

        // 3. Setup real-time validation
        setupRealTimeValidation()

        // 4. Setup tombol submit dengan long press
        setupButtons()
    }


    // FUNGSI 1: INISIALISASI VIEW

    private fun initViews() {
        tilNama = findViewById(R.id.tilNama)
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword)

        etNama = findViewById(R.id.etNama)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        rgJenisKelamin = findViewById(R.id.rgJenisKelamin)
        tvJenisKelaminError = findViewById(R.id.tvJenisKelaminError)

        cbMembaca = findViewById(R.id.cbMembaca)
        cbOlahraga = findViewById(R.id.cbOlahraga)
        cbMusik = findViewById(R.id.cbMusik)
        cbGaming = findViewById(R.id.cbGaming)
        cbMasak = findViewById(R.id.cbMasak)
        cbTravel = findViewById(R.id.cbTravel)
        tvHobiError = findViewById(R.id.tvHobiError)

        spinnerKota = findViewById(R.id.spinnerKota)
        tvKotaError = findViewById(R.id.tvKotaError)

        btnSubmit = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)
    }


    // FUNGSI 2: SETUP SPINNER

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            daftarKota
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKota.adapter = adapter

        // Validasi saat spinner berubah
        spinnerKota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                if (pos != 0) {
                    tvKotaError.visibility = View.GONE
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    // FUNGSI 3: REAL-TIME VALIDATION

    private fun setupRealTimeValidation() {

        // --- Validasi Nama (real-time) ---
        etNama.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val nama = s.toString().trim()
                when {
                    nama.isEmpty() -> tilNama.error = "Nama tidak boleh kosong"
                    nama.length < 3 -> tilNama.error = "Nama minimal 3 karakter"
                    else -> {
                        tilNama.error = null
                        tilNama.isErrorEnabled = false
                    }
                }
            }
        })

        //Validasi Email (real-time)
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString().trim()
                when {
                    email.isEmpty() -> tilEmail.error = "Email tidak boleh kosong"
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                        tilEmail.error = "Format email tidak valid"
                    else -> {
                        tilEmail.error = null
                        tilEmail.isErrorEnabled = false
                    }
                }
            }
        })

        //  Validasi Password (real-time)
        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val pass = s.toString()
                when {
                    pass.isEmpty() -> tilPassword.error = "Password tidak boleh kosong"
                    pass.length < 8 -> tilPassword.error = "Password minimal 8 karakter"
                    else -> {
                        tilPassword.error = null
                        tilPassword.isErrorEnabled = false
                    }
                }
                // Re-validasi confirm password jika sudah diisi
                val confirmPass = etConfirmPassword.text.toString()
                if (confirmPass.isNotEmpty()) {
                    if (pass != confirmPass) {
                        tilConfirmPassword.error = "Password tidak cocok"
                    } else {
                        tilConfirmPassword.error = null
                        tilConfirmPassword.isErrorEnabled = false
                    }
                }
            }
        })

        // Validasi Confirm Password (real-time)
        etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val confirmPass = s.toString()
                val pass = etPassword.text.toString()
                when {
                    confirmPass.isEmpty() -> tilConfirmPassword.error = "Konfirmasi password tidak boleh kosong"
                    confirmPass != pass -> tilConfirmPassword.error = "Password tidak cocok"
                    else -> {
                        tilConfirmPassword.error = null
                        tilConfirmPassword.isErrorEnabled = false
                    }
                }
            }
        })

        // Validasi Checkbox Hobi (real-time)
        val checkboxListener = CompoundButton.OnCheckedChangeListener { _, _ ->
            val jumlahDipilih = getSelectedHobi().size
            if (jumlahDipilih >= 3) {
                tvHobiError.visibility = View.GONE
            }
        }
        cbMembaca.setOnCheckedChangeListener(checkboxListener)
        cbOlahraga.setOnCheckedChangeListener(checkboxListener)
        cbMusik.setOnCheckedChangeListener(checkboxListener)
        cbGaming.setOnCheckedChangeListener(checkboxListener)
        cbMasak.setOnCheckedChangeListener(checkboxListener)
        cbTravel.setOnCheckedChangeListener(checkboxListener)

        //  Validasi RadioGroup (real-time)
        rgJenisKelamin.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                tvJenisKelaminError.visibility = View.GONE
            }
        }
    }


    // FUNGSI 4: SETUP BUTTONS

    private fun setupButtons() {

        // NORMAL CLICK: Submit
        btnSubmit.setOnClickListener {
            if (validateAllFields()) {
                showConfirmationDialog()
            }
        }

        // LONG PRESS: Tampilkan info tambahan
        btnSubmit.setOnLongClickListener {
            showLongPressDialog()
            true
        }

        // Reset Button
        btnReset.setOnClickListener {
            showResetConfirmation()
        }
    }


    // FUNGSI 5: VALIDASI SEMUA FIELD
    private fun validateAllFields(): Boolean {
        var isValid = true

        // Validasi Nama
        val nama = etNama.text.toString().trim()
        when {
            nama.isEmpty() -> {
                tilNama.error = "Nama tidak boleh kosong"
                isValid = false
            }
            nama.length < 3 -> {
                tilNama.error = "Nama minimal 3 karakter"
                isValid = false
            }
            else -> tilNama.error = null
        }

        // Validasi Email
        val email = etEmail.text.toString().trim()
        when {
            email.isEmpty() -> {
                tilEmail.error = "Email tidak boleh kosong"
                isValid = false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                tilEmail.error = "Format email tidak valid (contoh: user@email.com)"
                isValid = false
            }
            else -> tilEmail.error = null
        }

        // Validasi Password
        val password = etPassword.text.toString()
        when {
            password.isEmpty() -> {
                tilPassword.error = "Password tidak boleh kosong"
                isValid = false
            }
            password.length < 8 -> {
                tilPassword.error = "Password minimal 8 karakter"
                isValid = false
            }
            else -> tilPassword.error = null
        }

        // Validasi Confirm Password
        val confirmPassword = etConfirmPassword.text.toString()
        when {
            confirmPassword.isEmpty() -> {
                tilConfirmPassword.error = "Konfirmasi password tidak boleh kosong"
                isValid = false
            }
            confirmPassword != password -> {
                tilConfirmPassword.error = "Password tidak cocok!"
                isValid = false
            }
            else -> tilConfirmPassword.error = null
        }

        // Validasi Jenis Kelamin
        if (rgJenisKelamin.checkedRadioButtonId == -1) {
            tvJenisKelaminError.text = "Pilih jenis kelamin!"
            tvJenisKelaminError.visibility = View.VISIBLE
            isValid = false
        } else {
            tvJenisKelaminError.visibility = View.GONE
        }

        // Validasi Hobi (min 3)
        val selectedHobi = getSelectedHobi()
        if (selectedHobi.size < 3) {
            tvHobiError.text = " Pilih minimal 3 hobi! (Dipilih: ${selectedHobi.size})"
            tvHobiError.visibility = View.VISIBLE
            isValid = false
        } else {
            tvHobiError.visibility = View.GONE
        }

        // Validasi Spinner Kota
        if (spinnerKota.selectedItemPosition == 0) {
            tvKotaError.text = " Pilih kota asal!"
            tvKotaError.visibility = View.VISIBLE
            isValid = false
        } else {
            tvKotaError.visibility = View.GONE
        }

        return isValid
    }


    // FUNGSI 6: HELPER — AMBIL HOBI TERPILIH

    private fun getSelectedHobi(): List<String> {
        val selected = mutableListOf<String>()
        if (cbMembaca.isChecked) selected.add(" Membaca")
        if (cbOlahraga.isChecked) selected.add(" Olahraga")
        if (cbMusik.isChecked) selected.add(" Musik")
        if (cbGaming.isChecked) selected.add(" Gaming")
        if (cbMasak.isChecked) selected.add(" Memasak")
        if (cbTravel.isChecked) selected.add(" Traveling")
        return selected
    }

    // FUNGSI 7: ALERT DIALOG KONFIRMASI

    private fun showConfirmationDialog() {
        val namaLengkap = etNama.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val jenisKelamin = if (rgJenisKelamin.checkedRadioButtonId == R.id.rbLakiLaki)
            " Laki-laki" else " Perempuan"
        val hobi = getSelectedHobi().joinToString(", ")
        val kota = spinnerKota.selectedItem.toString()

        val message = """
            Pastikan data berikut sudah benar:
            
             Nama     : $namaLengkap
             Email    : $email
             Kelamin  : $jenisKelamin
             Hobi     : $hobi
             Kota     : $kota
            
            Lanjutkan pendaftaran?
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("✅ Konfirmasi Pendaftaran")
            .setMessage(message)
            .setPositiveButton("Ya, Daftar!") { dialog, _ ->
                dialog.dismiss()
                showSuccessDialog(namaLengkap)
            }
            .setNegativeButton("Periksa Lagi") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    // FUNGSI 8: DIALOG SUKSES SETELAH DAFTAR

    private fun showSuccessDialog(nama: String) {
        AlertDialog.Builder(this)
            .setTitle("🎉 Pendaftaran Berhasil!")
            .setMessage("Selamat, $nama!\n\nAkun kamu berhasil didaftarkan.\nSilakan cek email untuk verifikasi.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                resetForm()
            }
            .setCancelable(false)
            .show()
    }

    // FUNGSI 9: LONG PRESS DIALOG

    private fun showLongPressDialog() {
        val items = arrayOf(
            " Lihat Preview Data",
            " Simpan sebagai Draft",
            " Reset Form",
            "️ Bantuan"
        )

        AlertDialog.Builder(this)
            .setTitle(" Aksi Tambahan")
            .setItems(items) { dialog, which ->
                when (which) {
                    0 -> showPreviewData()
                    1 -> showDraftSaved()
                    2 -> showResetConfirmation()
                    3 -> showHelp()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showPreviewData() {
        val nama = etNama.text.toString().ifEmpty { "(belum diisi)" }
        val email = etEmail.text.toString().ifEmpty { "(belum diisi)" }
        val jenisKelamin = when (rgJenisKelamin.checkedRadioButtonId) {
            R.id.rbLakiLaki -> "Laki-laki"
            R.id.rbPerempuan -> "Perempuan"
            else -> "(belum dipilih)"
        }
        val hobi = getSelectedHobi().ifEmpty { listOf("(belum dipilih)") }.joinToString(", ")
        val kota = if (spinnerKota.selectedItemPosition == 0) "(belum dipilih)"
        else spinnerKota.selectedItem.toString()

        AlertDialog.Builder(this)
            .setTitle("📋 Preview Data")
            .setMessage("Nama: $nama\nEmail: $email\nKelamin: $jenisKelamin\nHobi: $hobi\nKota: $kota")
            .setPositiveButton("Tutup", null)
            .show()
    }

    private fun showDraftSaved() {
        Toast.makeText(this, "💾 Data tersimpan sebagai draft!", Toast.LENGTH_SHORT).show()
    }

    private fun showHelp() {
        AlertDialog.Builder(this)
            .setTitle("ℹ️ Bantuan")
            .setMessage("""
                 Cara mengisi form:
                
                1. Isi semua field yang tersedia
                2. Password minimal 8 karakter
                3. Pilih jenis kelamin
                4. Pilih minimal 3 hobi
                5. Pilih kota asal
                6. Tekan tombol DAFTAR
                
                💡 Tip: Long press tombol DAFTAR untuk opsi tambahan!
            """.trimIndent())
            .setPositiveButton("Mengerti", null)
            .show()
    }


    // FUNGSI 10: RESET FORM

    private fun showResetConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Reset Form")
            .setMessage("Yakin ingin menghapus semua data yang sudah diisi?")
            .setPositiveButton("Ya, Reset") { dialog, _ ->
                resetForm()
                dialog.dismiss()
                Toast.makeText(this, " Form berhasil direset!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun resetForm() {
        // Clear TextInput
        etNama.text?.clear()
        etEmail.text?.clear()
        etPassword.text?.clear()
        etConfirmPassword.text?.clear()

        // Clear errors
        tilNama.error = null; tilNama.isErrorEnabled = false
        tilEmail.error = null; tilEmail.isErrorEnabled = false
        tilPassword.error = null; tilPassword.isErrorEnabled = false
        tilConfirmPassword.error = null; tilConfirmPassword.isErrorEnabled = false

        // Reset RadioGroup
        rgJenisKelamin.clearCheck()
        tvJenisKelaminError.visibility = View.GONE

        // Reset Checkboxes
        cbMembaca.isChecked = false
        cbOlahraga.isChecked = false
        cbMusik.isChecked = false
        cbGaming.isChecked = false
        cbMasak.isChecked = false
        cbTravel.isChecked = false
        tvHobiError.visibility = View.GONE

        // Reset Spinner
        spinnerKota.setSelection(0)
        tvKotaError.visibility = View.GONE
    }
}
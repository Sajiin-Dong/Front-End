package com.example.sajiindong.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sajiindong.R
import com.example.sajiindong.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegisterActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var etUsername: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var auth: FirebaseAuth
    private lateinit var usersReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        etUsername = findViewById(R.id.etUsername)
        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tvLogin)
        progressBar = findViewById(R.id.progressBar)
        hideActionBar()

        auth = FirebaseAuth.getInstance()
        usersReference = FirebaseDatabase.getInstance().getReference("users")

        // Membuat teks "Already have an Account! Login" menjadi hyperlink
        val spannable = SpannableStringBuilder("Already have an Account! Login")
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                showProgressBar()
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish() // Selesai aktifitas RegisterActivity setelah kembali ke LoginActivity
            }
        }
        spannable.setSpan(
            clickableSpan,
            24,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvLogin.text = spannable
        tvLogin.movementMethod = LinkMovementMethod.getInstance()

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            val username = etUsername.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && username.isNotEmpty()) {
                showProgressBar()

                val query = usersReference.orderByChild("username").equalTo(username)

                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Username sudah terdaftar
                            hideProgressBar()
                            etUsername.error = "Username sudah digunakan!"
                        } else {
                            // Username belum terdaftar, lanjutkan proses registrasi
                            if (password.length > 6) {
                                if (confirmPassword == password) {
                                    auth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(this@RegisterActivity) { task ->
                                            if (task.isSuccessful) {
                                                val user = auth.currentUser

                                                // Simpan username ke database
                                                user?.let { firebaseUser ->
                                                    val userId = firebaseUser.uid
                                                    val userReference =
                                                        usersReference.child(userId)
                                                    val userMap = HashMap<String, Any>()
                                                    userMap["username"] = username
                                                    userReference.setValue(userMap)
                                                        .addOnCompleteListener { updateTask ->
                                                            if (updateTask.isSuccessful) {
                                                                // Kirim email verifikasi
                                                                user.sendEmailVerification()
                                                                    .addOnCompleteListener { verificationTask ->
                                                                        hideProgressBar()
                                                                        if (verificationTask.isSuccessful) {
                                                                            // Registrasi berhasil, alihkan ke LoginActivity
                                                                            startActivity(
                                                                                Intent(
                                                                                    applicationContext,
                                                                                    LoginActivity::class.java
                                                                                )
                                                                            )
                                                                            finish() // Selesai aktifitas RegisterActivity

                                                                            Toast.makeText(
                                                                                applicationContext,
                                                                                "Registrasi Berhasil. Silakan Cek Email Anda untuk Verifikasi.",
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()
                                                                        } else {
                                                                            // Jika gagal kirim email verifikasi
                                                                            Toast.makeText(
                                                                                applicationContext,
                                                                                "Gagal mengirim email verifikasi. Silakan coba lagi.",
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()
                                                                        }
                                                                    }
                                                            } else {
                                                                // Jika gagal menulis data ke database
                                                                hideProgressBar()
                                                                Toast.makeText(
                                                                    applicationContext,
                                                                    "Gagal menyimpan data pengguna.",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        }
                                                }
                                            } else {
                                                hideProgressBar()
                                                Toast.makeText(
                                                    applicationContext,
                                                    "Registrasi Gagal. ${task.exception?.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                } else {
                                    hideProgressBar()
                                    etConfirmPassword.error = "Password tidak sama!"
                                }
                            } else {
                                hideProgressBar()
                                etPassword.error = "Password harus lebih dari 6 karakter!"
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle jika query dibatalkan
                        hideProgressBar()
                        Toast.makeText(
                            applicationContext,
                            "Error: ${databaseError.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            } else {
                // Handle jika ada data yang kosong
                Toast.makeText(
                    applicationContext,
                    "Ada data yang masih kosong!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}

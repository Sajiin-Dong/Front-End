package com.example.sajiindong.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sajiindong.R
import com.example.sajiindong.ui.main.MainActivity
import com.example.sajiindong.ui.register.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var textViewRegister: TextView
    private lateinit var btnGoogleSignIn: SignInButton
    private lateinit var loginLayout: ConstraintLayout
    private lateinit var loadingLayout: ConstraintLayout

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    // Activity Result Launcher
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        textViewRegister = findViewById(R.id.textViewRegister)
        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn)
        loginLayout = findViewById(R.id.loginLayout)
        loadingLayout = findViewById(R.id.loadingLayout)

        hideActionBar()

        mAuth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        btnGoogleSignIn.setSize(SignInButton.SIZE_WIDE)
        btnGoogleSignIn.setColorScheme(SignInButton.COLOR_LIGHT)

        // Set click listener for Google Sign-In button
        btnGoogleSignIn.setOnClickListener {
            signIn()
        }

        // Initialize Activity Result Launcher for Google Sign-In
        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleGoogleSignInResult(task)
        }

        // Initialize and set SpannableString for register text
        val registerText = "You don't have an account? Register here"
        val spannableString = SpannableString(registerText)
        val registerHere = object : ClickableSpan() {
            override fun onClick(widget: View) {
                showLoading(true)
                Handler(Looper.getMainLooper()).postDelayed({
                    showLoading(false)
                    startActivity(Intent(applicationContext, RegisterActivity::class.java))
                }, 1000)
            }
        }

        val startIndex = registerText.indexOf("Register here")
        val endIndex = startIndex + "Register here".length

        if (startIndex != -1) {
            spannableString.setSpan(registerHere, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(ForegroundColorSpan(Color.DKGRAY), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        textViewRegister.text = spannableString
        textViewRegister.movementMethod = LinkMovementMethod.getInstance()

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                showLoading(true)
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    showLoading(false)
                    if (task.isSuccessful) {
                        if (mAuth.currentUser?.isEmailVerified == true) {
                            Toast.makeText(applicationContext, "Login Berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                        } else {
                            etEmail.error = "Verifikasi Email Anda Terlebih Dahulu"
                        }
                    } else {
                        Toast.makeText(applicationContext, "Login Gagal. Pastikan Email dan Password Anda Benar.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(applicationContext, "Email dan Password tidak boleh kosong.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signIn() {
        showLoading(true)
        val signInIntent = mGoogleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account?.let { firebaseAuthWithGoogle(it) }
        } catch (e: ApiException) {
            showLoading(false)
            Log.w("LoginActivity", "Google sign in failed", e)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                showLoading(false)
                if (task.isSuccessful) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    Toast.makeText(applicationContext, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loadingLayout.visibility = View.VISIBLE
            loginLayout.visibility = View.GONE
        } else {
            loadingLayout.visibility = View.GONE
            loginLayout.visibility = View.VISIBLE
        }
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}

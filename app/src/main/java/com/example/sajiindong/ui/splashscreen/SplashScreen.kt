package com.example.sajiindong.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.sajiindong.constants.Constants
import com.example.sajiindong.databinding.ActivitySplashScreenBinding
import com.example.sajiindong.preference.LoginPreference
import com.example.sajiindong.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var mLoginPreference: LoginPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mLoginPreference = LoginPreference(this)

        // Panggil navigate method dengan intent ke LoginActivity
        val loginIntent = Intent(this, LoginActivity::class.java)
        navigate(loginIntent)
    }

    private fun navigate(intent: Intent) {
        val splashTimer: Long = Constants.SPLASH_SCREEN_TIMER
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, splashTimer)
    }
}


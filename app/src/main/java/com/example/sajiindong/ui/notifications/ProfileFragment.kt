package com.example.sajiindong.ui.notifications

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sajiindong.databinding.FragmentProfileBinding
import com.example.sajiindong.ui.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var usersReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth
        usersReference = FirebaseDatabase.getInstance().getReference("users")

        loadUserData()
        languageHandler()
        logoutHandler()

        // Set WindowInsets for the ImageView to handle status bar padding
        binding.imageView.setOnApplyWindowInsetsListener { view, insets ->
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = insets.systemWindowInsetTop
            view.layoutParams = params
            insets.consumeSystemWindowInsets()
        }

        return root
    }

    private fun loadUserData() {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            // Cek apakah login menggunakan Google
            if (user.providerData.any { it.providerId == GoogleAuthProvider.PROVIDER_ID }) {
                // Jika login menggunakan Google, dapatkan informasi akun Google
                val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
                handleGoogleSignIn(googleSignInAccount)
            } else {
                // Ambil data username dari Firebase Realtime Database
                val userId = user.uid
                val userReference = usersReference.child(userId)
                userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    @SuppressLint("SetTextI18n")
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val username = dataSnapshot.child("username").value.toString()
                            binding.nameTextView.text = username
                        } else {
                            binding.nameTextView.text = "Username tidak ditemukan"
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle error
                    }
                })
            }
        }
    }

    // Handle penggunaan Google SignIn
    private fun handleGoogleSignIn(account: GoogleSignInAccount?) {
        account?.let {
            val userName = account.displayName // Mendapatkan nama pengguna dari GoogleSignInAccount

            // Set nama pengguna ke TextView
            binding.nameTextView.text = userName
        }
    }

    private fun languageHandler() {
        binding.languageCardView.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    private fun logoutHandler() {
        binding.logoutCardView.setOnClickListener {
            FirebaseAuth.getInstance().signOut() // Logout dari FirebaseAuth
            val googleSignInClient = GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.DEFAULT_SIGN_IN)
            googleSignInClient.signOut().addOnCompleteListener {
                // Logout dari Google Sign-In
                // Redirect ke halaman Login setelah logout
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

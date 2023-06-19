package com.example.cloneuber.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.databinding.ActivityMainBinding
import com.example.cloneuber.domain.model.User
import com.example.cloneuber.ui.dialog.ProgressDialog
import com.example.cloneuber.ui.signup.SignUpActivity
import com.example.cloneuber.ui.util.hideKeyboard
import com.example.cloneuber.ui.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var progressDialog: ProgressDialog
    private lateinit var user: User
    private lateinit var userType: String

    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = User()

        progressDialog = ProgressDialog()
        userType = intent.getStringExtra("userType") ?: ""

        binding.btnSignIn.setOnClickListener { signIn(it) }
        binding.btnSignUp.setOnClickListener { signUp() }

        setupObserver()
    }

    private fun signIn(v: View) {
        v.hideKeyboard()
        user.email = binding.inputLayoutEmail.editText?.text.toString()
        user.password = binding.inputLayoutPassword.editText?.text.toString()
        signInViewModel.validateLogin(user)
    }

    private fun setupObserver() {
        signInViewModel.emailError.observe(this) { error ->
            binding.inputLayoutEmail.error = error
        }

        signInViewModel.passwordError.observe(this) { error ->
            binding.inputLayoutPassword.error = error
        }
        signInViewModel.loginResult.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    progressDialog.show(supportFragmentManager, "ProgressDialog")
                }
                is Resource.Success -> {
                    progressDialog.dismiss()
                    this.showToast("$it")
                }
                is Resource.Failure -> {
                    progressDialog.dismiss()
                    this.showToast(it.message)
                }
            }
        })

    }

    private fun signUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        intent.putExtra("userType", userType)
        startActivity(intent)
    }
}



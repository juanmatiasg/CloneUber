package com.example.cloneuber.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.cloneuber.MyApp
import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.databinding.ActivityMainBinding
import com.example.cloneuber.ui.progressDialog.ProgressDialog
import com.example.cloneuber.ui.util.hideKeyboard
import com.example.cloneuber.ui.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var progressDialog: ProgressDialog
    private lateinit var email: String
    private lateinit var password: String

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog()

        binding.button.setOnClickListener { signIn(it) }

        setupObserver()
    }


    private fun signIn(v: View) {
        v.hideKeyboard()
        email = binding.inputLayoutEmail.editText?.text.toString()
        password = binding.inputLayoutPassword.editText?.text.toString()
        loginViewModel.signInUser(email, password)
    }

    private fun setupObserver() {
        loginViewModel.loginResult.observe(this, Observer {
            when(it){
                is Resource.Loading ->{
                    progressDialog.show(supportFragmentManager, "ProgressDialog")
                }
                is Resource.Success ->{
                    progressDialog.dismiss()
                    this.showToast("$it")
                    Log.e("Firebase Success",it.toString())
                }
                is Resource.Failure ->{
                    this.showToast(it.toString())
                    Log.e("Firebase Failed",it.toString())

                }
            }
        })

    }
}



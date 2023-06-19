package com.example.cloneuber.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cloneuber.Constants
import com.example.cloneuber.R
import com.example.cloneuber.databinding.ActivityHomeBinding
import com.example.cloneuber.ui.login.SignInActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    private lateinit var selectedUserType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }


    private fun setListeners() {
        binding.buttonDriver.setOnClickListener {
            selectedUserType = "Driver"
            navigateSignInActivity(selectedUserType)
        }
        binding.buttonCustomer.setOnClickListener {
            selectedUserType = "Customer"
            navigateSignInActivity(selectedUserType)
        }
    }

    private fun navigateSignInActivity(type:String) {
        val intent = Intent(this,SignInActivity::class.java)
        intent.putExtra("userType",type)
        startActivity(intent)
    }

}
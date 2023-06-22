package com.example.cloneuber.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cloneuber.databinding.ActivityOnboardingBinding
import com.example.cloneuber.ui.signin.SignInActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding:ActivityOnboardingBinding
    private lateinit var selectedUserType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
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
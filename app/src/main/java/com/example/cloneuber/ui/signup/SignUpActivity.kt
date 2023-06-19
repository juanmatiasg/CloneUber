package com.example.cloneuber.ui.signup

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cloneuber.data.util.GalleryPermission
import com.example.cloneuber.data.util.ImageUtil
import com.example.cloneuber.databinding.ActivitySignUpBinding
import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.domain.model.User
import com.example.cloneuber.ui.dialog.ProgressDialog
import com.example.cloneuber.ui.util.hideKeyboard

import com.example.cloneuber.ui.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding


    private val signUpViewModel: SignUpViewModel by viewModels()
    private lateinit var galleryPermission: GalleryPermission
    private lateinit var loadingProgressBar: ProgressDialog
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = User()
        initGalleryPermission()

        loadingProgressBar = ProgressDialog()

        binding.btnSignUp.setOnClickListener { signUp(it) }
        binding.addPhoto.setOnClickListener { galleryPermission.launchAddPhotoClicked() }

        setupObserver()

    }

    private fun initGalleryPermission() {
        galleryPermission = GalleryPermission(this,activityResultRegistry)
        galleryPermission.setOnImageSelectedListener(object : GalleryPermission.OnImageSelectedListener {
            override fun onImageSelected(uri: Uri) {
                signUpViewModel.selectedPhotoUri.value = uri
                val bitmap = ImageUtil.getBitmapFromUri(contentResolver, uri)
                binding.addPhoto.setImageBitmap(bitmap)
            }
        })
    }


    private fun setupObserver() {

        signUpViewModel.fullNameError.observe(this){error->
            binding.textInputLayoutName.error = error
        }
        signUpViewModel.emailError.observe(this){error ->
            binding.textInputLayoutEmail.error = error
        }
        signUpViewModel.passwordError.observe(this){error ->
            binding.textInputLayoutPassword.error = error
        }

        signUpViewModel.confirmPasswordError.observe(this){error ->
            binding.textInputLayoutConfirmPassword.error = error
        }

        signUpViewModel.profileImageError.observe(this){error->
            if(error=="Selecciona una foto") {
                this.showToast("$error")
            }
        }

        signUpViewModel.signUpResult.observe(this) { result->
            when (result) {
                is Resource.Loading -> {
                    loadingProgressBar.show(supportFragmentManager, "Loading...")
                }
                is Resource.Success -> {
                    loadingProgressBar.dismiss()
                    this.showToast("Registro exitoso")
                    finish()
                }
                is Resource.Failure -> {
                    loadingProgressBar.dismiss()
                    this.showToast(result.message)
                }
            }
        }

    }

    private fun signUp(v: View) {
        v.hideKeyboard()

        user.name = binding.textInputLayoutName.editText?.text.toString()
        user.email = binding.textInputLayoutEmail.editText?.text.toString()
        user.password = binding.textInputLayoutPassword.editText?.text.toString()
        user.confirmPassword = binding.textInputLayoutConfirmPassword.editText?.text.toString()
        user.type = intent.getStringExtra("userType") ?:""

        signUpViewModel.validateSignUp(user)

    }

}
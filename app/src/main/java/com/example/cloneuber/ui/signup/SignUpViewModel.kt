package com.example.cloneuber.ui.signup

import android.content.Context
import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloneuber.data.util.ImageUtil
import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.domain.model.User
import com.example.cloneuber.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _signUpResult = MutableLiveData<Resource<User>>()
    val signUpResult: LiveData<Resource<User>> = _signUpResult

    private val _fullNameError = MutableLiveData<String?>()
    val fullNameError:LiveData<String?> = _fullNameError

    private val _emailError = MutableLiveData<String?>()
    val emailError:LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError:LiveData<String?> = _passwordError

    private val _confirmPasswordError = MutableLiveData<String?>()
    val confirmPasswordError:LiveData<String?> =_confirmPasswordError

    private val _profileImageError = MutableLiveData<String?>()
    val profileImageError : LiveData<String?> = _profileImageError

    var selectedPhotoUri = MutableLiveData<Uri>()

    private fun validateEmail(email: String): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS.matcher(email)

        if (email.isEmpty()) {
            _emailError.value = "The email field cannot be empty"
            return false
        } else if (!emailPattern.matches()) {
            _emailError.value = "Email is invalid"
            return false
        } else {
            _emailError.value = null
        }
        return true
    }

    private fun validatePassword(password: String): Boolean {
        if (password.isEmpty()) {
            _passwordError.value = "The password field cannot be empty"
            return false
        } else if (password.length < 6) {
            _passwordError.value = "The password must be at least 6 characters"
            return false
        } else {
            _passwordError.value = null
        }
        return true
    }

    private fun validateConfirmPassword(password: String, confirmPassowrd: String): Boolean {
        if (confirmPassowrd.isEmpty()) {
            _confirmPasswordError.value = "The confirm password field cannot be empty"
            return false
        } else if (password != confirmPassowrd) {
            _confirmPasswordError.value = "The passwords not equals"
            return false
        } else {
            _confirmPasswordError.value = null
        }
        return true
    }

    private fun isValidPhotoUri(): Boolean {
        val photoUriValue = selectedPhotoUri.value
        if (photoUriValue == null) {
            _profileImageError.value = "Selecciona una foto"
            return false
        } else {
            _profileImageError.value = null
        }
        return true
    }

    private fun validateFullName(fullName: String): Boolean {
        if (fullName.isEmpty()) {
            _fullNameError.value = "The fullname field cannot be empty"
            return false
        } else {
            _fullNameError.value = null
        }
        return true
    }


   fun validateSignUp(user: User) {

        val isFullNameValid = validateFullName(user.name!!)
        val isEmailValid = validateEmail(user.email!!)
        val isPasswordValid = validatePassword(user.password!!)
        val isConfirmPasswordValid = validateConfirmPassword(user.password!!, user.confirmPassword!!)
        val isValidPhotoUri = isValidPhotoUri()

        val isRegistrationValid = isFullNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid && isValidPhotoUri

        viewModelScope.launch {
            if (isRegistrationValid) {
                _signUpResult.value = Resource.Loading
                val profileImageBase64 = ImageUtil.openUri(context,selectedPhotoUri.value!!)
                _signUpResult.value = signUpUseCase.signUp(user.name!!, user.email!!, user.password!!, user.type!!, profileImageBase64)
            }
        }
    }
}
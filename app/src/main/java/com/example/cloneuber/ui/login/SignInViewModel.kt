package com.example.cloneuber.ui.login

import android.util.Patterns
import androidx.lifecycle.*
import com.example.cloneuber.domain.model.User
import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.domain.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val loginUseCase: SignInUseCase) : ViewModel() {



    private val _emailError = MutableLiveData<String?>()
    val emailError:LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError : LiveData<String?> = _passwordError

    private val _loginResult = MutableLiveData<Resource<User>>()
    val loginResult: LiveData<Resource<User>> = _loginResult


    private fun validateEmail(email: String):Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS.matcher(email)

        if (email.isEmpty()) {
            _emailError.value = "The email field cannot be empty"
        } else if (!emailPattern.matches()) {
            _emailError.value = "Email is invalid"
        } else {
            _emailError.value = null
        }
        return true
    }

    private fun validatePassword(password: String):Boolean{
        if (password.isEmpty()) {
            _passwordError.value = "The password field cannot be empty"
        } else if (password.length < 6) {
            _passwordError.value = "The password must be at least 6 characters"
        } else {
            _passwordError.value = null
        }
        return true
    }
    fun validateLogin(user: User) {
        val isValidEmail = validateEmail(user.email!!)
        val isValidPassowrd = validatePassword(user.password!!)
        val resultSignUp = isValidEmail && isValidPassowrd

        if (resultSignUp) {
            viewModelScope.launch {
                _loginResult.value = Resource.Loading
                val result = loginUseCase.signIn(user.email!!, user.password!!)
                _loginResult.value = result
            }
        }
    }

}
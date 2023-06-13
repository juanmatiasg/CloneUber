package com.example.cloneuber.ui.login

import android.util.Patterns
import androidx.lifecycle.*
import com.example.cloneuber.data.model.User
import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.domain.usecases.LoginUseCase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {


    private val _loginResult = MutableLiveData<Resource<User>>()
    val loginResult: LiveData<Resource<User>> = _loginResult

    fun signInUser(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Resource.Loading
            if(emptyField(email,password)) {
                val result = loginUseCase.signIn(email, password)
                _loginResult.value = result
            }
            else{
                Resource.Failure("Field Empty")
            }
        }
    }



    private fun emptyField(email:String,password:String) = email.isNotEmpty() && password.isNotEmpty()
    private fun emailFormatValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
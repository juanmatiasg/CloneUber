package com.example.cloneuber.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloneuber.R
import com.example.cloneuber.data.util.ImageUtil
import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.domain.model.User
import com.example.cloneuber.domain.usecases.GetUserDataUseCase
import com.example.cloneuber.domain.usecases.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(@ApplicationContext private val context:Context,private val getUserDataUseCase: GetUserDataUseCase,private val logoutUseCase: LogoutUseCase): ViewModel() {
    private val _user = MutableLiveData<Resource<User>?>()
    val user: LiveData<Resource<User>?> = _user

    private val _resultLogout = MutableLiveData<String>()
    val resultLogout :LiveData<String> =_resultLogout

    fun getUserData() {
        viewModelScope.launch {
            _user.value = Resource.Loading
            if(_user.value !=null){
                _user.value = getUserDataUseCase.fetchGetUserData()
            }
            else{
                _user.value = null
            }
        }

    }

    fun logout(){
        viewModelScope.launch {
            try {
                logoutUseCase()
                _resultLogout.value = context.getString(R.string.textViewSuccessLogout)
            } catch (e: Exception) {
                // Maneja cualquier error que ocurra durante el cierre de sesión
                _resultLogout.value = e.message ?: "Ha ocurrido un error"
            }
        }
    }
}
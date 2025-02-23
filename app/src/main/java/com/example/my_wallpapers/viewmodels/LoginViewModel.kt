package com.example.my_wallpapers.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_wallpapers.util.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private var _login = MutableSharedFlow<Response<FirebaseUser>>()
    val login = _login.asSharedFlow()

    private var _resetPass = MutableSharedFlow<Response<String>>()
    val resetPass = _resetPass.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _login.emit(Response.Loading())
        }
        if(email.isEmpty() || password.isEmpty()) {
            viewModelScope.launch {
                _login.emit(Response.Failure("Email or password is empty"))
            }
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                viewModelScope.launch {
                    it.user?.apply {
                        _login.emit(Response.Success(this))
                    }
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _login.emit(Response.Failure(it.message.toString()))
                }
            }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _resetPass.emit(Response.Loading())
        }
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                viewModelScope.launch {
                    _resetPass.emit(Response.Success(email))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _resetPass.emit(Response.Failure(it.message.toString()))
                }
            }
    }

}
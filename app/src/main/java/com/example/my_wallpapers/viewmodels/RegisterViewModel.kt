package com.example.my_wallpapers.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.example.my_wallpapers.model.User
import com.example.my_wallpapers.util.Constants
import com.example.my_wallpapers.util.RegisterValidation
import com.example.my_wallpapers.util.RegisterValidationFields
import com.example.my_wallpapers.util.Response
import com.example.my_wallpapers.util.checkValidationForEmail
import com.example.my_wallpapers.util.checkValidationForPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) :ViewModel() {
    private var _register = MutableStateFlow<Response<User>>(Response.UnSpecifies())
    val register  : Flow<Response<User>> = _register
    private var _validation = Channel<RegisterValidationFields>()
    val validation = _validation.receiveAsFlow()

    fun createAccount(user: User,password:String){

        if (checkValidation(user.email, password)) {
            viewModelScope.launch {
                _register.emit(Response.Loading())
            }
            firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener {
                    it.user?.let {
                        saveUserInfo(it.uid,user)
                    }
                }.addOnFailureListener {
                    viewModelScope.launch {
                        _register.emit(Response.Failure(it.message.toString()))
                    }
                }
        } else {
            val registerFieldsState = RegisterValidationFields(checkValidationForEmail(user.email), checkValidationForPassword(password))
            viewModelScope.launch {
                _validation.send(registerFieldsState)
            }
        }

    }

    private fun saveUserInfo(uid: String, user: User) {
        db.collection(Constants.USER_COLLECTION)
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                viewModelScope.launch {
                    _register.emit(Response.Success(user))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _register.emit(Response.Failure(it.message.toString()))
                }
            }
    }


    private fun checkValidation(email: String, password: String):Boolean{
        val emailValidation = checkValidationForEmail(email)
        val passwordValidation = checkValidationForPassword(password)
        val registerValidation = emailValidation is RegisterValidation.success && passwordValidation is RegisterValidation.success
        return registerValidation
    }
}
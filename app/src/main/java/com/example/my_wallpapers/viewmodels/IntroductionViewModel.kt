package com.example.my_wallpapers.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_wallpapers.R
import com.example.my_wallpapers.util.Constants
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth
) : ViewModel(){
    private var _navigation = MutableStateFlow(0)
    val navigation : StateFlow<Int> = _navigation
    companion object {
        const val  SHOPPING_FRAGMENT = 23
        val ACTION_OFTEN_FRAGMENT = R.id.action_introductionFragment_to_accountOptionFragment
    }
    init {
        val isButtonClick = sharedPreferences.getBoolean(Constants.INTRODUCTION_EN, false)
        val user = firebaseAuth.currentUser

        if(user != null){
            viewModelScope.launch {
                _navigation.emit(SHOPPING_FRAGMENT)
            }
        } else if(isButtonClick){
            viewModelScope.launch {
                _navigation.emit(ACTION_OFTEN_FRAGMENT)
            }
        } else {
            Unit
        }
    }
    fun startButtonClick() {
        sharedPreferences.edit().putBoolean(Constants.INTRODUCTION_EN, true).apply()
    }
}
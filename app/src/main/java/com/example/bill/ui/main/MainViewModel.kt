package com.example.bill.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = com.example.bill.repository.repository.getInstance(application)

    val State = repository.Now_State()


}

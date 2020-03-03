package com.example.bill.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class repository(
    private val prefs: SharedPreferences,
    private val excutor: Executor
){

    companion object{
        const val SERVER_URL = "sch-hj.iptime.org:3350/bill"
        private val PREF = "pref"
        private val PREF_USER_NAME = "username"
        private val PREF_IMG = ""

        private var instance: repository? = null

        fun getInstance(context: Context): repository{
            return instance ?: synchronized(this){
                instance ?: repository(
                    context.getSharedPreferences(PREF, Context.MODE_PRIVATE),
                    Executors.newFixedThreadPool(64)
                ).also { instance = it }
            }
        }
    }

    private val StateListeners = mutableListOf<(State) -> Unit>()

    fun Now_State():LiveData<State>{
        return object : LiveData<State>(){
            private val listener = {state: State->
                postValue(state)
            }

            init {
                val username = prefs.getString(PREF_USER_NAME, null)
                val img = prefs.getString(PREF_IMG, null)
                val URL = SERVER_URL

                value = when{
                    username.isNullOrBlank() -> State.MainMenu
                    URL.isNullOrBlank() -> State.Cammera(username)
                    else -> State.ConnectedServer(username, img, URL)
                }
            }

            override fun onActive() {
                StateListeners.add(listener)
            }

            override fun onInactive() {
                StateListeners.remove(listener)
            }
        }
    }

    fun Sending(username: String, img: String, send: MutableLiveData<Boolean>){
        excutor.execute {
            send.postValue(true)
            try {
            }finally {
                send.postValue(false)
            }
        }
    }

}
package com.example.bill.repository

import android.content.Context
import androidx.lifecycle.LiveData

class repository(){

    companion object{
        const val SERVER_URL = "sch-hj.iptime.org:3350/bill"


        private var instance: repository? = null

        fun getInstance(context: Context): repository{
            return instance ?: synchronized(this){
                instance ?: repository().also { instance = it }
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
                val username = ""
                val img = ""
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

}
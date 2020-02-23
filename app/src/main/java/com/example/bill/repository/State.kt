package com.example.bill.repository

sealed class State{
    object MainMenu : State()

    data class ConnectedServer(
        val username: String,
        val img: String,
        val URL: String
    ) : State()

    data class Cammera(
        val username: String
    ) : State()
}
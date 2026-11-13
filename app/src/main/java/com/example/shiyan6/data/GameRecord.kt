package com.example.shiyan6.data

import java.io.Serializable

class GameRecord : Serializable {
    var gamesPlayed: Int = 0
    var wins: Int = 0
    var losses: Int = 0
    var totalFlips: Int = 0
    var bestFlips: Int = Int.MAX_VALUE

    companion object {
        private const val serialVersionUID = 1L
    }
}

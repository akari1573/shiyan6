package com.example.shiyan6.fragment

class ThreeCardGameFragment : CardGameFragment() {

    override fun cardCount(): Int = 3

    override fun gameTitle(): String = "3张牌翻牌游戏"

    override fun statFileName(): String = "game3.dat"
}

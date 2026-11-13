package com.example.shiyan6.fragment

class TwoCardGameFragment : CardGameFragment() {

    override fun cardCount(): Int = 2

    override fun gameTitle(): String = "2张牌翻牌游戏"

    override fun statFileName(): String = "game2.dat"
}

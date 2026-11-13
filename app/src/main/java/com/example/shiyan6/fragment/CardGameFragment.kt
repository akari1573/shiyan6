package com.example.shiyan6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.shiyan6.R
import com.example.shiyan6.data.GameRecord
import com.example.shiyan6.util.FileStore
import kotlin.random.Random

abstract class CardGameFragment : Fragment() {

    protected abstract fun cardCount(): Int
    protected abstract fun gameTitle(): String
    protected abstract fun statFileName(): String

    private lateinit var tvTitle: TextView
    private lateinit var tvResult: TextView
    private lateinit var tvStats: TextView
    private lateinit var cards: Array<TextView>

    private var targetIndex = 0
    private var flips = 0
    private var gameOver = false
    private var record = GameRecord()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_card_game, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitle = view.findViewById(R.id.tv_title)
        tvResult = view.findViewById(R.id.tv_result)
        tvStats = view.findViewById(R.id.tv_stats)
        cards = arrayOf(
            view.findViewById(R.id.card0),
            view.findViewById(R.id.card1),
            view.findViewById(R.id.card2)
        )

        tvTitle.text = gameTitle()
        for (i in cardCount() until 3) {
            cards[i].visibility = View.GONE
        }
        cards.forEachIndexed { index, card ->
            card.setOnClickListener { onCardClick(index) }
        }
        view.findViewById<Button>(R.id.btn_restart).setOnClickListener { restart() }

        record = FileStore.load<GameRecord>(requireContext(), statFileName()) ?: GameRecord()
        updateStats()
        restart()
    }

    private fun onCardClick(index: Int) {
        if (gameOver || flips >= maxFlips() || cards[index].text.toString() != "?") return

        flips++
        val isTarget = index == targetIndex
        cards[index].setBackgroundResource(
            if (isTarget) R.drawable.card_heart else R.drawable.card_spade
        )
        cards[index].text = if (isTarget) "♥" else "♠"
        cards[index].setTextColor(
            if (isTarget) 0xFFE53935.toInt() else 0xFF455A64.toInt()
        )

        when {
            isTarget -> {
                gameOver = true
                record.gamesPlayed++
                record.wins++
                record.totalFlips += flips
                if (flips < record.bestFlips) record.bestFlips = flips
                tvResult.text = "恭喜！第 $flips 次翻牌就找到了目标牌 ♥，本局获胜！"
                revealAll()
            }
            flips >= maxFlips() -> {
                gameOver = true
                record.gamesPlayed++
                record.losses++
                record.totalFlips += flips
                tvResult.text = "很遗憾，机会用完，未找到目标牌，本局失败"
                revealAll()
            }
            else -> {
                tvResult.text = "翻到 ♠，还剩 ${maxFlips() - flips} 次翻牌机会"
            }
        }
        updateStats()
        FileStore.save(requireContext(), statFileName(), record)
    }

    private fun restart() {
        gameOver = false
        flips = 0
        targetIndex = Random.nextInt(cardCount())
        for (i in 0 until cardCount()) {
            cards[i].text = "?"
            cards[i].setBackgroundResource(R.drawable.card_back)
            cards[i].setTextColor(0xFFFFFFFF.toInt())
        }
        tvResult.text = "请点击卡片翻牌，找到目标牌 ♥ 即获胜"
    }

    private fun revealAll() {
        for (i in 0 until cardCount()) {
            if (cards[i].text.toString() == "?") {
                val isTarget = i == targetIndex
                cards[i].setBackgroundResource(
                    if (isTarget) R.drawable.card_heart else R.drawable.card_spade
                )
                cards[i].text = if (isTarget) "♥" else "♠"
                cards[i].setTextColor(
                    if (isTarget) 0xFFE53935.toInt() else 0xFF455A64.toInt()
                )
            }
        }
    }

    private fun maxFlips(): Int = cardCount() - 1

    private fun updateStats() {
        val best = if (record.bestFlips == Int.MAX_VALUE) "--" else record.bestFlips.toString()
        tvStats.text = "历史记录：总局数 ${record.gamesPlayed}，胜 ${record.wins}，" +
                "负 ${record.losses}，总翻牌 ${record.totalFlips} 次，最少翻牌 $best 次"
    }
}

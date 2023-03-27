package com.jzheng20.minesweeper.dataStorage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val scoreList = MutableLiveData<ArrayList<Score>>()
    private val _wins = MutableLiveData<Int>()
    private val _loses = MutableLiveData<Int>()
    private val _games = MutableLiveData<Int>()
    private val _inGameScore = MutableLiveData<Int>()
    private val _theme = MutableLiveData<String>()
    private val _text = MutableLiveData<String>()

    init {
        _wins.value = 0
        _games.value = 0
        _loses.value = 0
        _inGameScore.value = 0
        _theme.value = "#FFFFFF"
        _text.value = "#000000"
    }

    val theme: LiveData<String> = _theme
    val text: LiveData<String> = _text
    val inGameScore: LiveData<Int> = _inGameScore
    val wins: LiveData<Int> = _wins
    val loses: LiveData<Int> = _loses
    val games: LiveData<Int> = _games
    fun add(player: String, difficulty: Int, reveals: Int, win: Boolean) {
        val tempList = scoreList.value ?: arrayListOf()
        tempList.add(Score(player, difficulty, reveals, win))
        scoreList.value = tempList
    }

    fun setTheme(theme: Int) {
        when (theme) {
            0 -> _theme.value = "#FFFFFF"
            1 -> _theme.value = "#57D3FF"
            2 -> _theme.value = "#FFFFAB"
            3 -> _theme.value = "#92D050"
        }

    }
    fun setText(theme: Int) {
        when (theme) {
            0 -> _text.value = "#000000"
            1 -> _text.value = "#7030A0"
            2 -> _text.value = "#0900C4"
        }

    }

    fun setWins(wins: Int) {
        _wins.value = wins
    }

    fun setGames(games: Int) {
        _games.value = games
    }

    fun setLoses(loses: Int) {
        _loses.value = loses
    }

    fun setInGameScore(score: Int) {
        _inGameScore.value = score
    }
}

data class Score(val player: String, val difficulty: Int, val reveals: Int, val win: Boolean)
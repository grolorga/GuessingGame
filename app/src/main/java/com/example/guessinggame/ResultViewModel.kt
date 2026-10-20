package com.example.guessinggame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultViewModel(
    val won: Boolean,
    val word: String,
    val wrongGuesses: Int,
    val maximumWrongGuesses: Int,
) : ViewModel()

class ResultViewModelFactory(
    private val won: Boolean,
    private val word: String,
    private val wrongGuesses: Int,
    private val maximumWrongGuesses: Int,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            "Unknown ViewModel class: ${modelClass.name}"
        }
        @Suppress("UNCHECKED_CAST")
        return ResultViewModel(won, word, wrongGuesses, maximumWrongGuesses) as T
    }
}

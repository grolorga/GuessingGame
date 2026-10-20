package com.example.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val game = WordGame(WORDS.random())

    private val _secretWord = MutableLiveData(game.hiddenWord)
    val secretWord: LiveData<String> = _secretWord

    private val _livesLeft = MutableLiveData(game.livesLeft)
    val livesLeft: LiveData<Int> = _livesLeft

    private val _incorrectLetters = MutableLiveData("")
    val incorrectLetters: LiveData<String> = _incorrectLetters

    /** Two-way bound to the text field by @={gameViewModel.guess}. */
    val guess = MutableLiveData("")

    private val _inputError = MutableLiveData<GuessSubmission?>(null)
    val inputError: LiveData<GuessSubmission?> = _inputError

    private val _gameOver = MutableLiveData(false)
    val gameOver: LiveData<Boolean> = _gameOver

    val answer: String
        get() = game.answer

    val isWon: Boolean
        get() = game.isWon

    val wrongGuesses: Int
        get() = game.wrongGuesses

    val maximumWrongGuesses: Int
        get() = game.maximumWrongGuesses

    fun makeGuess() {
        val trimmed = guess.value.orEmpty().trim()
        if (trimmed.isEmpty()) {
            _inputError.value = GuessSubmission.EMPTY
            return
        }
        if (trimmed.length != 1) {
            _inputError.value = GuessSubmission.NOT_ONE_LETTER
            return
        }

        when (game.guess(trimmed.single())) {
            GuessOutcome.ALREADY_GUESSED -> {
                _inputError.value = GuessSubmission.ALREADY_GUESSED
                return
            }
            GuessOutcome.NOT_A_LETTER -> {
                _inputError.value = GuessSubmission.NOT_ONE_LETTER
                return
            }
            GuessOutcome.CORRECT,
            GuessOutcome.INCORRECT,
            GuessOutcome.WON,
            GuessOutcome.LOST -> Unit
        }

        _inputError.value = null
        guess.value = ""
        publishGameState()
        if (game.isWon || game.isLost) finishGame()
    }

    fun finishGame() {
        _gameOver.value = true
    }

    private fun publishGameState() {
        _secretWord.value = game.hiddenWord
        _livesLeft.value = game.livesLeft
        _incorrectLetters.value = game.incorrectLetters.joinToString(separator = ", ")
    }

    companion object {
        private val WORDS = listOf(
            "ANDROID",
            "ACTIVITY",
            "FRAGMENT",
            "KOTLIN",
            "LAYOUT",
            "LIFECYCLE",
            "NAVIGATION",
            "VIEWMODEL",
        )
    }
}

enum class GuessSubmission {
    EMPTY,
    NOT_ONE_LETTER,
    ALREADY_GUESSED,
}

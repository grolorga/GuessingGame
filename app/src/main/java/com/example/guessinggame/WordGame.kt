package com.example.guessinggame

/** The model: the rules and mutable data for one round. It has no Android dependencies. */
class WordGame(
    val answer: String,
    val maximumWrongGuesses: Int = 8,
) {
    private val guessedLetters = linkedSetOf<Char>()

    var wrongGuesses: Int = 0
        private set

    val livesLeft: Int
        get() = maximumWrongGuesses - wrongGuesses

    val incorrectLetters: List<Char>
        get() = guessedLetters.filterNot { it in answer.uppercase() }

    val hiddenWord: String
        get() = answer.uppercase().map { character ->
            when {
                !character.isLetter() -> character
                character in guessedLetters -> character
                else -> '_'
            }
        }.joinToString(separator = " ")

    val isWon: Boolean
        get() = answer.uppercase()
            .filter(Char::isLetter)
            .all(guessedLetters::contains)

    val isLost: Boolean
        get() = livesLeft == 0

    fun guess(letter: Char): GuessOutcome {
        check(!isWon && !isLost) { "The round has already finished" }

        val normalized = letter.uppercaseChar()
        if (!normalized.isLetter()) return GuessOutcome.NOT_A_LETTER
        if (!guessedLetters.add(normalized)) return GuessOutcome.ALREADY_GUESSED

        return if (normalized in answer.uppercase()) {
            if (isWon) GuessOutcome.WON else GuessOutcome.CORRECT
        } else {
            wrongGuesses++
            if (isLost) GuessOutcome.LOST else GuessOutcome.INCORRECT
        }
    }
}

enum class GuessOutcome {
    CORRECT,
    INCORRECT,
    ALREADY_GUESSED,
    NOT_A_LETTER,
    WON,
    LOST,
}

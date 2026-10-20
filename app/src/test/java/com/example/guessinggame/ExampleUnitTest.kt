package com.example.guessinggame

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class WordGameTest {
    @Test
    fun guessesRevealLettersAndTrackLives() {
        val game = WordGame(answer = "KOTLIN", maximumWrongGuesses = 2)

        assertEquals(GuessOutcome.CORRECT, game.guess('k'))
        assertEquals("K _ _ _ _ _", game.hiddenWord)
        assertEquals(GuessOutcome.INCORRECT, game.guess('A'))
        assertEquals(1, game.livesLeft)
        assertEquals(listOf('A'), game.incorrectLetters)
        assertFalse(game.isLost)

        listOf('O', 'T', 'L').forEach(game::guess)
        game.guess('I')
        assertEquals(GuessOutcome.WON, game.guess('N'))
        assertTrue(game.isWon)
    }
}

package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.unscramble.data.allWords
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
// UI state của game
data class GameUiState(
    val currentScrambledWord: String = ""
)


class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

    // Lưu từ hiện tại
    private lateinit var currentWord: String

    // Set lưu các từ đã dùng
    private var usedWords: MutableSet<String> = mutableSetOf()

    init {
        resetGame()
    }

    /** Reset lại game */
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    /** Chọn từ random và xáo chữ */
    private fun pickRandomWordAndShuffle(): String {
        if (usedWords.size == allWords.size) {
            usedWords.clear() // hoặc xử lý gameOver
        }
        val unused = allWords.filterNot { usedWords.contains(it) }
        currentWord = unused.random()
        usedWords.add(currentWord)
        return shuffleCurrentWord(currentWord)
    }

    private fun shuffleCurrentWord(word: String): String {
        if (word.length <= 1) return word
        val chars = word.toCharArray()
        do {
            chars.shuffle()
        } while (String(chars) == word)
        return String(chars)
    }
    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

}

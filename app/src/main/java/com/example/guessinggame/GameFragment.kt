package com.example.guessinggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = requireNotNull(_binding)
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.inputError.observe(viewLifecycleOwner) { error ->
            binding.letterLayout.error = when (error) {
                GuessSubmission.EMPTY -> getString(R.string.empty_letter_error)
                GuessSubmission.NOT_ONE_LETTER -> getString(R.string.single_letter_error)
                GuessSubmission.ALREADY_GUESSED -> getString(R.string.duplicate_letter_error)
                null -> null
            }
        }
        viewModel.gameOver.observe(viewLifecycleOwner) { isGameOver ->
            if (isGameOver) navigateToResult()
        }
    }

    private fun navigateToResult() {
        findNavController().navigate(
            R.id.action_gameFragment_to_resultFragment,
            Bundle().apply {
                putBoolean(ResultFragment.ARG_WON, viewModel.isWon)
                putString(ResultFragment.ARG_WORD, viewModel.answer)
                putInt(ResultFragment.ARG_WRONG_GUESSES, viewModel.wrongGuesses)
                putInt(
                    ResultFragment.ARG_MAXIMUM_WRONG_GUESSES,
                    viewModel.maximumWrongGuesses,
                )
            },
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

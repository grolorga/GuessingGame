package com.example.guessinggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.guessinggame.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = requireArguments()
        val factory = ResultViewModelFactory(
            won = args.getBoolean(ARG_WON),
            word = requireNotNull(args.getString(ARG_WORD)),
            wrongGuesses = args.getInt(ARG_WRONG_GUESSES),
            maximumWrongGuesses = args.getInt(ARG_MAXIMUM_WRONG_GUESSES),
        )
        val viewModel = ViewModelProvider(this, factory)[ResultViewModel::class.java]
        binding.resultViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.playAgainButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_WON = "won"
        const val ARG_WORD = "word"
        const val ARG_WRONG_GUESSES = "wrongGuesses"
        const val ARG_MAXIMUM_WRONG_GUESSES = "maximumWrongGuesses"
    }
}

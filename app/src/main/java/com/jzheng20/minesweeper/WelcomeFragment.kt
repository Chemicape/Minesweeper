package com.jzheng20.minesweeper

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jzheng20.minesweeper.dataStorage.SharedViewModel

class WelcomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }
    private lateinit var startButton: Button
    private lateinit var settingButton: Button
    private lateinit var scoreButton: Button
    val viewModel : SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        view.setBackgroundColor(Color.parseColor(viewModel.theme.value))
        startButton = view.findViewById(R.id.newgame)
        startButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_welcomeFragment_to_configurationFragment)
        }
        settingButton = view.findViewById(R.id.setting)
        settingButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_welcomeFragment_to_settingsFragment)
        }
        scoreButton = view.findViewById(R.id.scoreboard)
        scoreButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_welcomeFragment_to_statisticsFragment)
        }
        return view
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            WelcomeFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}
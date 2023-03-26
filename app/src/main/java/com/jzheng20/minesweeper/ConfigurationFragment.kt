package com.jzheng20.minesweeper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class ConfigurationFragment : Fragment() {
    private lateinit var easyButton: Button
    private lateinit var normalButton: Button
    private lateinit var hardButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_configuration, container, false)
        easyButton = view.findViewById(R.id.easy)
        normalButton =view.findViewById(R.id.normal)
        hardButton = view.findViewById(R.id.hard)
        easyButton.setOnClickListener {
            val bundle = Bundle()
            val navController = findNavController()
            view.findNavController().navigate(R.id.action_configurationFragment_to_gameFragment,bundle.apply {
                bundle.putInt("Difficulty",0)
            })
        }
        normalButton.setOnClickListener {
            val bundle = Bundle()
            val navController = findNavController()
            view.findNavController().navigate(R.id.action_configurationFragment_to_gameFragment,bundle.apply {
                bundle.putInt("Difficulty",1)
            })
        }
        hardButton.setOnClickListener {
            val bundle = Bundle()
            val navController = findNavController()
            view.findNavController().navigate(R.id.action_configurationFragment_to_gameFragment,bundle.apply {
                bundle.putInt("Difficulty",2)
            })
        }
        return view
    }
}
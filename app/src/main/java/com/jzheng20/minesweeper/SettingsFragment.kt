package com.jzheng20.minesweeper

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Color.parseColor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.jzheng20.minesweeper.dataStorage.SharedViewModel

class SettingsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }
    private lateinit var back:Button
    private lateinit var radioGroup: RadioGroup
    val viewModel : SharedViewModel by activityViewModels()
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        viewModel.theme.observe(viewLifecycleOwner){
            view.setBackgroundColor(parseColor(viewModel.theme.value))
        }
        view.setBackgroundColor(Color.parseColor(viewModel.theme.value))
        radioGroup = view.findViewById(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // 执行相应的操作
            when (checkedId) {
                R.id.white -> {
                    viewModel.setTheme(0)
                }
                R.id.lightblue -> {
                    viewModel.setTheme(1)
                }
                R.id.lightyellow -> {
                    viewModel.setTheme(2)
                }
                R.id.lime -> {
                    viewModel.setTheme(3)
                }
            }
        }
        radioGroup.checkedRadioButtonId
        back = view.findViewById(R.id.set_back)
        back.setOnClickListener {
            view.findNavController().navigate(R.id.action_settingsFragment_to_welcomeFragment)
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}
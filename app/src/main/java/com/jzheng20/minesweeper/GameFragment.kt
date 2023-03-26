package com.jzheng20.minesweeper

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jzheng20.minesweeper.model.MineSweeperModel

class GameFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }
    private lateinit var back: Button
    private lateinit var textView: TextView
    private lateinit var gridLayout: GridLayout
    private lateinit var board:MineSweeperModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        val difficulty = arguments?.getInt("Difficulty")
        back = view.findViewById(R.id.game_back)
        back.isVisible = false
        back.isEnabled = false
        textView = view.findViewById(R.id.difficulty)
        if(difficulty==0){
            textView.text = "Easy"
            board = MineSweeperModel(10)
        }else if(difficulty==1){
            textView.text = "Normal"
            board = MineSweeperModel(15)
        }else if(difficulty==2){
            textView.text = "Hard"
            board = MineSweeperModel(20)
        }
        back.setOnClickListener {
            view.findNavController().navigate(R.id.action_gameFragment_to_welcomeFragment)
        }
        gridLayout = view.findViewById(R.id.gridLayout)
        for (i in 0 until gridLayout.rowCount) {
            for (j in 0 until gridLayout.columnCount) {
                val imageButton = ImageButton(requireContext())
                imageButton.setImageResource(R.drawable.covered)
                imageButton.layoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(i), GridLayout.spec(j)
                ).apply {
                    width = 110
                    height = 110
                    setMargins(0, 0, 0, 0)
                }
                imageButton.setOnClickListener {
                    val row = i
                    val column = j
                    processor(board,row,column,imageButton,back)
                }
//                imageButton.setOnLongClickListener {
//                    imageButton.setImageResource(R.drawable.flagged)
//                    true
//                }
                gridLayout.addView(imageButton, i * gridLayout.columnCount + j)
            }
        }
        return view
    }
    fun processor(board:MineSweeperModel,x:Int,y:Int,button: ImageButton,newGame:Button){
        val result = board.processor(x,y)
        if(result==-1){
            button.setImageResource(R.drawable.bomb)
            gameOver(false)
        }else{
            button.setImageResource(getDrawable(result))
            if(board.win()){
                gameOver(true)
            }
        }
    }
    fun getDrawable(num:Int):Int{
        if(num==0){
            return R.drawable.zerobomb
        }else if(num==1){
            return R.drawable.onebomb
        }else if(num==2){
            return R.drawable.twobomb
        }else if(num==3){
            return R.drawable.threebomb
        }else if(num==4){
            return R.drawable.fourbomb
        }else if(num==5){
            return R.drawable.fivebomb
        }else if(num==6){
            return R.drawable.sixbomb
        }else if(num==7){
            return R.drawable.sevenbomb
        }else if(num==8){
            return R.drawable.eightbomb
        }
        return 0
    }
    fun gameOver(win: Boolean){
        if(win){
            textView.text = "Win!"
        }else{
            textView.text="Lose..."
        }
    }
    fun scan(){

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}
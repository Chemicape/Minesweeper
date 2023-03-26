package com.jzheng20.minesweeper

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
        val difficulty = arguments?.getInt("Difficulty") as Int
        back = view.findViewById(R.id.game_back)
        back.isVisible = false
        back.isEnabled = false
        textView = view.findViewById(R.id.difficulty)
        when (difficulty){
            0 -> textView.text = "Very easy"
            1 -> textView.text = "Easy"
            2 -> textView.text = "Normal"
            3 -> textView.text = "Hard"
            4 -> textView.text = "Extreme"
        }
        board = MineSweeperModel(3*(difficulty+2))
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
                }
                imageButton.scaleType = ImageView.ScaleType.CENTER
                imageButton.setOnClickListener {
                    val row = i
                    val column = j
                    processor(board,row,column,imageButton,back)
                }
                imageButton.setOnLongClickListener {
//                    if()
                    imageButton.setImageResource(R.drawable.flagged)
                    true
                }
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
        }else if(result==0){
            for (i in 0 until gridLayout.rowCount) {
                for (j in 0 until gridLayout.columnCount) {
                    if(board.isRevealed(i,j)){
                        val button = gridLayout.getChildAt(i*10+j) as ImageButton
                        button.setImageResource(getDrawable(board.processor(i,j)))
                    }
                }
            }
            if(board.win()){
                gameOver(true)
            }
        } else{
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
        back.isEnabled = true
        back.isVisible = true
        if(win){
            textView.text = "Win!"
        }else{
            textView.text="Lose..."

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}
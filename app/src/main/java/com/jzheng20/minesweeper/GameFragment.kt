package com.jzheng20.minesweeper

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.jzheng20.minesweeper.dataStorage.Score
import com.jzheng20.minesweeper.dataStorage.SharedViewModel
import com.jzheng20.minesweeper.model.MineSweeperModel

class GameFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }
    private var difficulty:Int = 0
    private var revealed:Int = 0
    private var player = ""
    private var win = false
    private lateinit var back: Button
    private lateinit var textView: TextView
    private lateinit var gridLayout: GridLayout
    private lateinit var board:MineSweeperModel
    private lateinit var playerName:EditText
    private lateinit var imageView: ImageView
    private var gameOver:Boolean = false
    private lateinit var score:TextView
    val viewModel :SharedViewModel by activityViewModels()
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        view.setBackgroundColor(Color.parseColor(viewModel.theme.value))
        difficulty = arguments?.getInt("Difficulty") as Int
        score = view.findViewById(R.id.score)
        score.text = "0"
        viewModel.inGameScore.observe(viewLifecycleOwner){
            score.text = ""+viewModel.inGameScore.value as Int
        }
        playerName = view.findViewById(R.id.player_name)
        imageView = view.findViewById(R.id.imageView)
        imageView.isVisible = false
        playerName.isVisible = false
        playerName.isEnabled = false
        back = view.findViewById(R.id.game_back)
        back.isVisible = false
        back.isEnabled = false
        textView = view.findViewById(R.id.difficulty)
        when (difficulty){
            0 -> textView.text = resources.getString(R.string.veryeasy)
            1 -> textView.text = resources.getString(R.string.easy)
            2 -> textView.text = resources.getString(R.string.normal)
            3 -> textView.text = resources.getString(R.string.hard)
            4 -> textView.text = resources.getString(R.string.extreme)
        }
        board = MineSweeperModel(3*(difficulty+2))

        back.setOnClickListener {
            score.text = "0"
            player =  playerName.text.toString()
            addUpScore(player,difficulty,revealed,win)
            view.findNavController().navigate(R.id.action_gameFragment_to_welcomeFragment)
        }
        gridLayout = view.findViewById(R.id.gridLayout)
        for (i in 0 until gridLayout.rowCount) {
            for (j in 0 until gridLayout.columnCount) {
                val imageButton = ImageButton(requireContext())
                imageButton.setImageResource(R.drawable.covered)
                imageButton.adjustViewBounds = true
                imageButton.scaleType = ImageView.ScaleType.CENTER
                imageButton.layoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(i), GridLayout.spec(j)
                ).apply {
                    width = 110
                    height = 110
                }

                imageButton.setOnClickListener {
                    if(!gameOver){
                    val row = i
                    val column = j
                    processor(board,row,column,imageButton)
                }
                }
//                imageButton.setOnLongClickListener {
//                    if()
//                    imageButton.setImageResource(R.drawable.flagged)
//                    true
//                }
                gridLayout.addView(imageButton, i * gridLayout.columnCount + j)
            }
        }
        return view
    }
    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

        }
    }

    fun addUpScore(player: String, difficulty: Int, reveals: Int, win: Boolean) {
        viewModel.add(player, difficulty, reveals, win)
        viewModel.setGames(viewModel.games.value as Int+1)
        if (win) {
            viewModel.setWins(viewModel.wins.value as Int+1)
        } else {
            viewModel.setLoses(viewModel.loses.value as Int+1)
        }
    }

    fun processor(board:MineSweeperModel,x:Int,y:Int,button: ImageButton){
        val isRevealed = board.isRevealed(x,y)
        val result = board.processor(x,y)
        if(result==-1){
            button.setImageResource(R.drawable.bomb)
            gameOver(false,x,y)
        }else if(result==0){
            revealed = 0
            for (i in 0 until gridLayout.rowCount) {
                for (j in 0 until gridLayout.columnCount) {
                    if(board.isRevealed(i,j)){
                        val button = gridLayout.getChildAt(i*10+j) as ImageButton
                        button.setImageResource(getDrawable(board.processor(i,j)))
                        revealed++
                    }
                }
            }
            if(board.win()){
                gameOver(true,x,y)
            }
        } else{
            if(!isRevealed){revealed++}
            button.setImageResource(getDrawable(result))
            if(board.win()){
                gameOver(true,x,y)
            }
        }
        viewModel.setInGameScore(revealed*(difficulty+5))
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
    fun gameOver(win: Boolean,x:Int,y:Int){
        gameOver = true
        for (i in 0 until gridLayout.rowCount) {
            for (j in 0 until gridLayout.columnCount) {
                if(board.isRevealed(i,j)){
                    val button = gridLayout.getChildAt(i*10+j) as ImageButton
                    button.isEnabled = false
                }
            }
        }
        if(win){
            for (i in 0 until gridLayout.rowCount) {
                for (j in 0 until gridLayout.columnCount) {
                    if(board.isBomb(i,j)){
                        val button = gridLayout.getChildAt(i*10+j) as ImageButton
                        dropBlocks(button,win)
                    }
                }
            }
        }else{
            for (i in 0 until gridLayout.rowCount) {
                for (j in 0 until gridLayout.columnCount) {
                    if(board.isBomb(i,j)){
                        if(i==x&&y==j){}else{
                        val button = gridLayout.getChildAt(i*10+j) as ImageButton
                        dropBlocks(button,win)
                    }}
                }
            }
        }
        this.win = win
        back.isEnabled = true
        back.isVisible = true
        playAnimation(win)
        playerName.isEnabled = true
        playerName.isVisible = true
    }
    fun playAnimation(win: Boolean){
        imageView.isVisible = true
        if(win){imageView.setImageResource(R.drawable.youwin)}
        else{imageView.setImageResource(R.drawable.youlose)}
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener {
            val value = it.animatedValue as Float
            imageView.alpha = value
        }
        animator.interpolator = LinearInterpolator()
        animator.duration = 2000L
        animator.start()
    }
    fun dropBlocks(button: ImageButton,win:Boolean){
        if(win) {
            button.setImageResource(R.drawable.untriggerd)
        }
        val fadeOut = ObjectAnimator.ofFloat(button, "alpha", 1f, 0f)
        fadeOut.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) { }
            override fun onAnimationCancel(p0: Animator) { }
            override fun onAnimationRepeat(p0: Animator) { }
            override fun onAnimationEnd(p0: Animator) {
                if(win){
                button.setImageResource(R.drawable.flower)
                }else{
                    button.setImageResource(R.drawable.untriggerd)
                }
            }
        })
        val fadeIn = ObjectAnimator.ofFloat(button, "alpha", 0f, 1f)
        val set = AnimatorSet()
        set.play(fadeOut).before(fadeIn)
        set.duration = 2000
        set.start()
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}
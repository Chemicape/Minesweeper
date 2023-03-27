package com.jzheng20.minesweeper

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color.parseColor
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jzheng20.minesweeper.dataStorage.Score
import com.jzheng20.minesweeper.dataStorage.SharedViewModel

class StatisticsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }
    private lateinit var back:Button
    private lateinit var totalgames:TextView
    private lateinit var totalwins:TextView
    private lateinit var totalloses:TextView
    private lateinit var recycler: RecyclerView
    private lateinit var prefs: SharedPreferences
    val viewModel :SharedViewModel by activityViewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        view.setBackgroundColor(parseColor(viewModel.theme.value))
        prefs = PreferenceManager.getDefaultSharedPreferences(view.context)
        recycler = view.findViewById(R.id.stat_recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.smoothScrollToPosition(prefs.getInt(ConfigurationFragment.POSITION, 0))
        back = view.findViewById(R.id.stat_back)
        totalgames = view.findViewById(R.id.totalgames)
        totalwins = view.findViewById(R.id.totalwins)
        totalloses = view.findViewById(R.id.totalloses)
        viewModel.scoreList.observe(viewLifecycleOwner) {
            recycler.adapter = Adapter(it)
        }
        viewModel.games.observe(viewLifecycleOwner){
            totalgames.text = viewModel.games.value.toString()
        }
        viewModel.wins.observe(viewLifecycleOwner){
            totalwins.text = viewModel.wins.value.toString()
        }
        viewModel.loses.observe(viewLifecycleOwner){
            totalloses.text = viewModel.loses.value.toString()
        }

        back.setOnClickListener {
            view.findNavController().navigate(R.id.action_statisticsFragment_to_welcomeFragment)
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var score: Score
        private val wordTextView: TextView = itemView.findViewById(R.id.score_text)
        init {
        }

        override fun onClick(p0: View?) {
            prefs.edit().putInt(POSITION, adapterPosition).apply()
        }

        fun bind(score: Score) {
            this.score = score
            var name = score.player
            if(score.player.equals("")){
                name = "    "
            }

            var win:String
            var difficulty:String = ""
            var scores:Int
            if(score.win){
                win = resources.getString(R.string.win)
            }else{
                win = resources.getString(R.string.lose)
            }
            when (score.difficulty){
                0 -> difficulty = resources.getString(R.string.veryeasy)
                1 -> difficulty = resources.getString(R.string.easy)
                2 -> difficulty = resources.getString(R.string.normal)
                3 -> difficulty = resources.getString(R.string.hard)
                4 -> difficulty = resources.getString(R.string.extreme)
            }
            if(score.win){
                scores = 100*score.difficulty+(score.difficulty+5)*score.reveals
            }else{
                scores = (score.difficulty+5)*score.reveals
            }
            wordTextView.text = name+"    "+difficulty+"    "+win+"    "+resources.getString(R.string.score)+scores
        }
    }

    private inner class Adapter(private val list: List<Score>) :
        RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = layoutInflater.inflate(R.layout.stat_score, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount() = list.size
    }
    companion object {
        fun newInstance() = WelcomeFragment()
        const val POSITION = "adapter_position"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatisticsFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}
package com.jzheng20.minesweeper

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jzheng20.minesweeper.dataStorage.SharedViewModel

class ConfigurationFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var prefs: SharedPreferences
    val viewModel : SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_configuration, container, false)
        view.setBackgroundColor(Color.parseColor(viewModel.theme.value))
        prefs = PreferenceManager.getDefaultSharedPreferences(view.context)

        recycler = view.findViewById(R.id.recycler_view)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.smoothScrollToPosition(prefs.getInt(POSITION, 0))
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            recycler.adapter = Adapter()

    }
    private inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private var difficulty: Int = 0
        private val wordTextView: TextView = itemView.findViewById(R.id.difficulty_textView)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val bundle = Bundle()
            val navController = findNavController()
            navController.navigate(R.id.action_configurationFragment_to_gameFragment,bundle.apply {
                bundle.putInt("Difficulty",difficulty)
            })
            prefs.edit().putInt(POSITION, adapterPosition).apply()
        }

        fun bind(difficulty:Int) {
            this.difficulty = difficulty
            when(difficulty){
                0 -> wordTextView.text = resources.getString(R.string.veryeasy)
                1 -> wordTextView.text = resources.getString(R.string.easy)
                2 -> wordTextView.text = resources.getString(R.string.normal)
                3 -> wordTextView.text = resources.getString(R.string.hard)
                4 -> wordTextView.text = resources.getString(R.string.extreme)
            }

        }
    }

    private inner class Adapter() :
        RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = layoutInflater.inflate(R.layout.recycler_item, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(position)
        }

        override fun getItemCount() = 5
    }
    companion object {
        fun newInstance() = WelcomeFragment()
        const val POSITION = "adapter_position"
    }
}
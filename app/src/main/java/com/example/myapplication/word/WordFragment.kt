package com.example.myapplication.word

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.FabActionListener
import com.example.myapplication.MyApplication
import com.example.myapplication.NewWordFragment
import com.example.myapplication.R
import com.example.myapplication.database.model.Word
import com.example.myapplication.databinding.FragmentWordBinding

class WordFragment() : Fragment() {

    private var _binding: FragmentWordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var favAction: FabActionListener

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((context?.applicationContext as MyApplication).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FabActionListener) {
            favAction = context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_WordFragment_to_NewWordFragment)
        }

        val adapter = WordListAdapter()
        binding.recyclerview.apply {
            setAdapter(adapter)
            layoutManager = LinearLayoutManager(context)
        }

        wordViewModel.allWords.observe(viewLifecycleOwner) { words ->
            words?.let {
                adapter.submitList(it)
            }
        }

        setFragmentResultListener("newWordRequest") { key, bundle ->
            bundle.getString(NewWordFragment.EXTRA_REPLY)?.let { reply ->
                // ...
                val word = Word(reply)
                wordViewModel.insert(word)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
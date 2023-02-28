package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentNewWordBinding

class NewWordFragment : Fragment() {

    private var _binding: FragmentNewWordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var favAction: FabActionListener

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

        _binding = FragmentNewWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSave.setOnClickListener {
            Toast.makeText(context, "テストです", Toast.LENGTH_SHORT).show()
            val editWord = binding.editWord
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWord.text)) {
                //setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWord.text.toString()
                //replyIntent.putExtra(EXTRA_REPLY, word)
                //setResult(Activity.RESULT_OK, replyIntent)
                setFragmentResult(
                    "newWordRequest",
                    bundleOf(
                        EXTRA_REPLY to word
                    )
                )

            }
            findNavController().popBackStack()
            //finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
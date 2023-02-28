package com.example.myapplication

import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentSpeechRecognizerBinding

class SpeechRecognizerFragment : Fragment() {

    private var _binding: FragmentSpeechRecognizerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: SpeechRecognizerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpeechRecognizerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SpeechRecognizerViewModel::class.java]

        val granted = ContextCompat.checkSelfPermission(requireContext(), RECORD_AUDIO)
        if (granted != PackageManager.PERMISSION_GRANTED) {
            view.apply {
                binding.recognizeStartButton.isEnabled = false

                binding.recognizeStopButton.isEnabled = false
            }
            requestPermissionLauncher.launch(RECORD_AUDIO)
        } else {
            initialization()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initialization() {
        viewModel.initialize()
        viewModel.recognizeText.observe(viewLifecycleOwner) {
            view?.apply {
                binding.recognizeTextView
                    .text = it
            }
        }

        view?.apply {
            binding.recognizeStartButton.apply {
                isEnabled = true
                setOnClickListener {
                    viewModel.start()
                }
            }

            binding.recognizeStopButton.apply {
                isEnabled = true
                setOnClickListener {
                    viewModel.stop()
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            initialization()
        } else {
            // 拒否されたら何かしらダイアログを表示する
            // 設定から権限を許可してくださいみたいな
        }
    }

}
package com.example.myapplication

import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class SpeechRecognizerFragment : Fragment() {
    companion object {
        fun newInstance() = SpeechRecognizerFragment()
    }

    private lateinit var viewModel: SpeechRecognizerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_speech_recognizer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SpeechRecognizerViewModel::class.java]

        val granted = ContextCompat.checkSelfPermission(requireContext(), RECORD_AUDIO)
        if (granted != PackageManager.PERMISSION_GRANTED) {
            view.apply {
                findViewById<Button>(R.id.recognize_start_button).isEnabled = false

                findViewById<Button>(R.id.recognize_stop_button).isEnabled = false
            }
            requestPermissionLauncher.launch(RECORD_AUDIO)
        } else {
            initialization()
        }
    }

    private fun initialization() {
        viewModel.initialize()
        viewModel.recognizeText.observe(viewLifecycleOwner) {
            view?.apply {
                findViewById<TextView>(R.id.recognize_text_view)?.text = it
            }
        }

        view?.apply {
            findViewById<Button>(R.id.recognize_start_button).apply {
                isEnabled = true
                setOnClickListener {
                    viewModel.start()
                }
            }

            findViewById<Button>(R.id.recognize_stop_button).apply {
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
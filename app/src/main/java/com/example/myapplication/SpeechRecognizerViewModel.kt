package com.example.myapplication

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SpeechRecognizerViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    private var speechRecognizer: SpeechRecognizer? = null

    val recognizeText = MutableLiveData<String>()

    override fun onCleared() {
        speechRecognizer?.destroy()
        speechRecognizer = null
        super.onCleared()
    }

    fun initialize() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplication())
        speechRecognizer?.setRecognitionListener(createRecognitionListenerStringStream {
            recognizeText.postValue(it)
            //recognize_text_view.text = it
        })
    }

    fun start() {
        speechRecognizer?.startListening(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH))
    }

    fun stop() {
        speechRecognizer?.stopListening()
    }

    /** 公開関数で受け取った TextView の更新処理を各関数で呼び出す*/
    private fun createRecognitionListenerStringStream(onResult: (String) -> Unit): RecognitionListener {
        return object : RecognitionListener {
            override fun onRmsChanged(rmsdB: Float) {
                /** 今回は特に利用しない */
            }

            override fun onReadyForSpeech(params: Bundle) {
                onResult("onReadyForSpeech")
            }

            override fun onBufferReceived(buffer: ByteArray) {
                onResult("onBufferReceived")
            }

            override fun onPartialResults(partialResults: Bundle) {
                onResult("onPartialResults")
            }

            override fun onEvent(eventType: Int, params: Bundle) {
                onResult("onEvent")
            }

            override fun onBeginningOfSpeech() {
                onResult("onBeginningOfSpeech")
            }

            override fun onEndOfSpeech() {
                onResult("onEndOfSpeech")
            }

            override fun onError(error: Int) {
                onResult("onError")
            }

            override fun onResults(results: Bundle) {
                val stringArray =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                onResult("onResults " + stringArray.toString())
            }
        }
    }

}
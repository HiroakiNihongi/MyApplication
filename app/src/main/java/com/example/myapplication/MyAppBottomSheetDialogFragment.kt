package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.DialogMyAppBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyAppBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DialogMyAppBinding.inflate(inflater, container, false)
        return binding.root
    }
}
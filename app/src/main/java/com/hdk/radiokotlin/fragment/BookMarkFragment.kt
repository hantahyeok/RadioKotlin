package com.hdk.radiokotlin.fragment

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import com.hdk.radiokotlin.R
import com.hdk.radiokotlin.databinding.FragmentBookMarkBinding
import com.hdk.radiokotlin.databinding.FragmentRadioBinding
import java.io.IOException

class BookMarkFragment : Fragment() {

    private lateinit var binding: FragmentBookMarkBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBookMarkBinding.inflate(layoutInflater)

        binding.btn.setOnClickListener {

        }

        return binding.root
    }
}
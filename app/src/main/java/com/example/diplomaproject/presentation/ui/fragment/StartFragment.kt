package com.example.diplomaproject.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.diplomaproject.R
import com.example.diplomaproject.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dropGame()
        startGame()
    }

    private fun startGame() {
        binding.btnFirst.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_taskBoardFragment)
        }
    }

    private fun dropGame() {
        binding.btnSecond.setOnClickListener {
            Toast.makeText(context, "Вы прошли мимо, игра не началась", Toast.LENGTH_SHORT).show()
        }
    }
}
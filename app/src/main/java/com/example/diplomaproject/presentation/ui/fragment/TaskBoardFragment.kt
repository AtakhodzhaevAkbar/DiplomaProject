package com.example.diplomaproject.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.diplomaproject.R
import com.example.diplomaproject.databinding.FragmentTaskBoardBinding

class TaskBoardFragment : Fragment() {
    private lateinit var binding: FragmentTaskBoardBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstMission()
        secondMission()
    }

    private fun secondMission() {
        binding.btnSecond.setOnClickListener{
            findNavController().navigate(R.id.action_taskBoardFragment_to_secondMissionFragment)
        }
    }

    private fun firstMission() {
        binding.btnFirst.setOnClickListener{
            findNavController().navigate(R.id.action_taskBoardFragment_to_firstMissionFragment)
        }
    }
}

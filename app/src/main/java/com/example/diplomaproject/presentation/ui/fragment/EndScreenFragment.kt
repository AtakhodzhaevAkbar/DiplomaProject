package com.example.diplomaproject.presentation.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.diplomaproject.R
import com.example.diplomaproject.databinding.FragmentLoseBinding

class EndScreenFragment : Fragment() {
    private lateinit var binding: FragmentLoseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoseBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deathReason = arguments?.getInt("Death")
        when (deathReason) {
            1 -> {
                binding.title.text = "Вы погибли в битве!"
                binding.headerImage.setImageResource(R.drawable.death)
            }

            3 -> {
                binding.title.text = "Вы были убиты ночью убийцами после побега от каравана!"
                binding.headerImage.setImageResource(R.drawable.death)
            }

            4 -> {
                binding.title.text =
                    "Вы откупились, но какой ценой. Ваша репутация испорчена вы больше не получите не одного заказа"
                binding.title.textSize = 30f
            }

            5 -> {
                binding.title.text =
                    "Вы отдали все свои деньги бандиту к сожалению вам осталось не на что жить"
                binding.headerImage.setImageResource(R.drawable.death)
            }

            6 -> {
                binding.title.text = "Не стоит поварачиваться к столь грозному противнику спиной"
                binding.headerImage.setImageResource(R.drawable.death)
            }
        }

        val winReason = arguments?.getInt("Win")
        when (winReason){
            1->{
                binding.headerImage.setImageResource(R.drawable.winner_screen)
                binding.title.text = "Вы победили!"
            }
        }
        binding.btnFirst.setOnClickListener {
            findNavController().navigate(R.id.action_loseFragment_to_startFragment)
        }
        binding.btnSecond.setOnClickListener {
            activity?.finish()
        }
    }
}
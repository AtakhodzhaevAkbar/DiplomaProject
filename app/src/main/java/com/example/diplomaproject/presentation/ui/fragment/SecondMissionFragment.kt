package com.example.diplomaproject.presentation.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.diplomaproject.R
import com.example.diplomaproject.databinding.FragmentSecondMissionBinding

class SecondMissionFragment : Fragment() {
    private lateinit var binding: FragmentSecondMissionBinding

    private var gold = 50
    private var health = 100
    private var dmg = 20
    private var state = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentSecondMissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButton()
    }

    @SuppressLint("SetTextI18n")
    private fun setupButton() {
            binding.btnFirst.setOnClickListener {
                when (state) {
                    0 -> {
                        binding.headerImage.setImageResource(R.drawable.forge)
                        binding.title.text = "Вы отправились в кузню"
                        binding.subhead.text = "Точно ли вы хотите купить новое снаряжение? Это отнимет у вас 40 золота"
                        binding.body.text = "Это добавит вам 30HP и 30DMG"
                        binding.btnFirst.text = "Купить"
                        binding.btnSecond.text = "Не покупать"
                        binding.dmg.text = "${dmg}dmg"
                        binding.health.text = "${health}HP"
                        binding.gold.text = "${gold}gold"
                        state = 1
                    }

                    1 -> {
                        gold -= 40
                        health += 30
                        dmg += 30
                        binding.headerImage.setImageResource(R.drawable.camp)
                        binding.title.text = "Вы закупились и отправились к лагерю"
                        binding.subhead.text = "Вы заметили лагерь"
                        binding.body.text = "Вы увидели лагерь к удивлению для вас вы видете что 1 бандит перебил всю свою банду ради всей добычи"
                        binding.dmg.text = "${dmg}dmg"
                        binding.health.text = "${health}HP"
                        binding.gold.text = "${gold}gold"
                        binding.btnFirst.text = "Продолжить"
                        binding.btnSecond.visibility = View.GONE
                        state = 2
                    }

                    2 -> {
                        binding.headerImage.setImageResource(R.drawable.stealth)
                        binding.title.text = "Вы тихо подходите к нему"
                        binding.subhead.text = "Вы наблюдаете за ним"
                        binding.body.text = "Вы наблюдаете за бандитом, видно что он очень хорошо экипирован и отлично натренирован. Что вы будете делать?"
                        binding.dmg.text = "${dmg}dmg"
                        binding.health.text = "${health}HP"
                        binding.gold.text = "${gold}gold"
                        binding.btnFirst.text = "Выйти к бандиту"
                        binding.btnSecond.visibility = View.VISIBLE
                        binding.btnSecond.text = "Убежать прочь"
                        state = 3
                    }

                    3 -> {
                        binding.headerImage.setImageResource(R.drawable.versus)
                        binding.title.text = "Вы вышли к бандиту"
                        binding.subhead.text = "Он заметил вас"
                        if (gold < 40) {
                            binding.body.text = "Он предлагает вам следующее: за 40 золота он может спокойно вас отпустить. У вас недостаточно денег. Что вы будете делать?"
                            binding.btnFirst.text = "Вступить в бой"
                            binding.btnSecond.text = "Убежать"
                            binding.btnSecond.setOnClickListener {
                                val bundle = Bundle().apply {
                                    putInt("Death", 5)
                                }
                                findNavController().navigate(R.id.action_secondMissionFragment_to_fightWithBanditsFragment, bundle)
                            }
                        } else {
                            binding.body.text = "Он предлагает вам следующее: за 40 золота он может спокойно вас отпустить. Вы согласны?"
                            binding.btnFirst.text = "Вступить в бой"
                            binding.btnSecond.text = "Откупиться"
                            binding.btnSecond.setOnClickListener {
                                val bundle = Bundle().apply {
                                    putInt("Death", 4)
                                }
                                findNavController().navigate(R.id.action_secondMissionFragment_to_loseFragment, bundle)
                            }
                        }
                        state = 4
                    }

                    4 -> {
                        val bundle = Bundle().apply {
                            putInt("gold", gold)
                            putInt("health", health)
                            putInt("dmg", dmg)
                        }
                        findNavController().navigate(
                            R.id.action_secondMissionFragment_to_fightWithBanditsFragment,
                            bundle
                        )
                    }
                }
            }

            binding.btnSecond.setOnClickListener {
                when (state) {
                    1 -> {
                        binding.headerImage.setImageResource(R.drawable.camp)
                        binding.title.text = "Вы отправились к лагерю"
                        binding.subhead.text = "Вы заметили лагерь"
                        binding.body.text = "Вы увидели лагерь к удивлению для вас вы видете что 1 бандит перебил всю свою банду ради всей добычи"
                        binding.dmg.text = "${dmg}dmg"
                        binding.health.text = "${health}HP"
                        binding.gold.text = "${gold}gold"
                        binding.btnFirst.text = "Продолжить"
                        binding.btnSecond.visibility = View.GONE
                        state = 2
                    }

                    3 -> {
                        binding.headerImage.setImageResource(R.drawable.versus)
                        binding.title.text = "Вы решили убежать в страхе"
                        binding.subhead.text = "Вы решили убежать от бандита"
                        binding.body.text = "Вы решили убежать от бандита, но из за шума он заметил вас и убил вас."
                        binding.btnFirst.text = "Продолжить"
                        binding.btnFirst.setOnClickListener {
                            val bundle = Bundle().apply {
                                putInt("Death", 6)
                            }
                            findNavController().navigate(R.id.action_secondMissionFragment_to_loseFragment, bundle)
                        }
                        binding.btnSecond.visibility = View.GONE
                    }
                }
            }
        }
    }
package com.example.diplomaproject.presentation.ui.fragment

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

    private var gold = "50 gold"
    private var health = "100HP"
    private var dmg = "20dmg"
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
                        binding.dmg.text = dmg
                        binding.health.text = health
                        binding.gold.text = gold
                        state = 1
                    }

                    1 -> {
                        gold = "10gold"
                        health = "130HP"
                        dmg = "50dmg"
                        binding.headerImage.setImageResource(R.drawable.caravan)
                        binding.title.text = "Вы закупились и отправились к лагерю"
                        binding.subhead.text = "Вы заметили лагерь"
                        binding.body.text = "Вы увидели лагерь к удивлению для вас вы видете что 1 бандит перебил всю свою банду ради всей добычи"
                        binding.dmg.text = dmg
                        binding.health.text = health
                        binding.gold.text = gold
                        binding.btnFirst.text = "Продолжить"
                        binding.btnSecond.visibility = View.GONE
                        state = 2
                    }

                    2 -> {
                        binding.headerImage.setImageResource(R.drawable.ambush)
                        binding.title.text = "Вы тихо подходите к нему"
                        binding.subhead.text = "Вы наблюдаете за ним"
                        binding.body.text = "Вы наблюдаете за бандитом, видно что он очень хорошо экипирован и отлично натренирован. Что вы будете делать?"
                        binding.dmg.text = dmg
                        binding.health.text = health
                        binding.gold.text = gold
                        binding.btnFirst.text = "Выйти к бандиту"
                        binding.btnSecond.visibility = View.VISIBLE
                        binding.btnSecond.text = "Убежать прочь"
                        state = 3
                    }

                    3 -> {
                        binding.headerImage.setImageResource(R.drawable.fight_with_bandits)
                        binding.title.text = "Вы вышли к бандиту"
                        binding.subhead.text = "Он заметил вас"
                        if (gold == "10gold") {
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
                            putString("gold", gold)
                            putString("health", health)
                            putString("dmg", dmg)
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
                        gold = "50 gold"
                        health = "100HP"
                        dmg = "20dmg"
                        binding.headerImage.setImageResource(R.drawable.caravan)
                        binding.title.text = "Вы отправились к лагерю"
                        binding.subhead.text = "Вы заметили лагерь"
                        binding.body.text = "Вы увидели лагерь к удивлению для вас вы видете что 1 бандит перебил всю свою банду ради всей добычи"
                        binding.dmg.text = dmg
                        binding.health.text = health
                        binding.gold.text = gold
                        binding.btnFirst.text = "Продолжить"
                        binding.btnSecond.visibility = View.GONE
                        state = 2
                    }

                    3 -> {
                        binding.headerImage.setImageResource(R.drawable.fight_with_bandits)
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
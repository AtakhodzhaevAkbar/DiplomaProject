package com.example.diplomaproject.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.diplomaproject.R
import com.example.diplomaproject.databinding.FragmentFirstMissionBinding

class FirstMissionFragment : Fragment() {
    private lateinit var binding: FragmentFirstMissionBinding
    private var gold = "50 gold"
    private var health = "100HP"
    private var dmg = "20dmg"
    private var state = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstMissionBinding.inflate(inflater, container, false)
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
                    binding.title.text = "Вы закупились и отправились к каравану"
                    binding.subhead.text = "Вы встретили караван"
                    binding.body.text = "Вы встретились с караваном и отправились в путь вместе с ними в качестве их охраны"
                    binding.dmg.text = dmg
                    binding.health.text = health
                    binding.gold.text = gold
                    binding.btnFirst.text = "Продолжить"
                    binding.btnSecond.visibility = View.GONE
                    state = 2
                }

                2 -> {
                    binding.headerImage.setImageResource(R.drawable.ambush)
                    binding.title.text = "В пути на вас напали бандиты"
                    binding.subhead.text = "Неожидано на вас напал 1 сильный бандит в латах"
                    binding.body.text = "В гущу леса на вас вдруг выскочил 1 бандит в прочных латах. Что вы будете делать?"
                    binding.dmg.text = dmg
                    binding.health.text = health
                    binding.gold.text = gold
                    binding.btnFirst.text = "Выйти к бандиту"
                    binding.btnSecond.visibility = View.VISIBLE
                    binding.btnSecond.text = "Бросить караван"
                    state = 3
                }

                3 -> {
                    binding.headerImage.setImageResource(R.drawable.fight_with_bandits)
                    binding.title.text = "Вы вышли к бандиту"
                    binding.subhead.text = "Бандит предлагает вам свои условия"
                    if (gold == "10gold") {
                        binding.body.text = "Он предлагает вам следующее: за 40 золота он может спокойно вас отпустить. У вас недостаточно денег. Что вы будете делать?"
                        binding.btnFirst.text = "Вступить в бой"
                        binding.btnSecond.text = "Убежать"
                        binding.btnSecond.setOnClickListener {
                            val bundle = Bundle().apply {
                                putInt("Death", 3)
                            }
                            findNavController().navigate(R.id.action_firstMissionFragment_to_loseFragment, bundle)
                        }
                    } else {
                        binding.body.text = "Он предлагает вам следующее: за 40 золота он может спокойно вас отпустить. Вы согласны?"
                        binding.btnFirst.text = "Вступить в бой"
                        binding.btnSecond.text = "Откупиться"
                        binding.btnSecond.setOnClickListener {
                            val bundle = Bundle().apply {
                                putInt("Death", 4)
                            }
                            findNavController().navigate(R.id.action_firstMissionFragment_to_loseFragment, bundle)
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
                        R.id.action_firstMissionFragment_to_fightWithBanditsFragment,
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
                    binding.title.text = "Вы отправились к каравану"
                    binding.subhead.text = "Вы встретили караван"
                    binding.body.text = "Вы встретились с караваном и отправились в путь вместе с ними в качестве их охраны"
                    binding.dmg.text = dmg
                    binding.health.text = health
                    binding.gold.text = gold
                    binding.btnFirst.text = "Продолжить"
                    binding.btnSecond.visibility = View.GONE
                    state = 2
                }

                3 -> {
                    binding.headerImage.setImageResource(R.drawable.fight_with_bandits)
                    binding.title.text = "Вы решили бросить караван"
                    binding.subhead.text = "Вы оставили караван и бросились прочь"
                    binding.body.text = "Вы оставили караван позади и бросились прочь. К сожалению для вас караванщик откупился от бандита и решил вам отомстить. Ночью к вам пробрались убийцы и убили вас."
                    binding.btnFirst.text = "Продолжить"
                    binding.btnFirst.setOnClickListener {
                        val bundle = Bundle().apply {
                            putInt("Death", 3)
                        }
                        findNavController().navigate(R.id.action_firstMissionFragment_to_loseFragment, bundle)
                    }
                    binding.btnSecond.visibility = View.GONE
                }
            }
        }
    }
}
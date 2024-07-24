package com.example.diplomaproject.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.diplomaproject.R
import com.example.diplomaproject.databinding.FragmentFightWithBanditsBinding
import kotlin.random.Random

class FightWithBanditsFragment : Fragment() {
    private lateinit var binding: FragmentFightWithBanditsBinding

    private var playerHealth: Int = 0
    private var playerDamage: Int = 0
    private var playerGold: Int = 0

    private var banditHealth = 300
    private val banditDamage = 35

    private var isDodge: Boolean = false
    private var isEnhancedAttack: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFightWithBanditsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = requireArguments()
        playerHealth = args.getString("health")?.replace("HP", "")?.toIntOrNull() ?: 0
        playerDamage = args.getString("dmg")?.replace("dmg", "")?.toIntOrNull() ?: 0
        playerGold = args.getString("gold")?.replace("gold", "")?.toIntOrNull() ?: 0

        updateUI()
        playerTurn()
    }

    private fun playerTurn() {
        binding.btnAttack.setOnClickListener {
            isEnhancedAttack = Random.nextInt(100) < 30
            val damageToDeal = if (isEnhancedAttack) playerDamage * 2 else playerDamage
            banditHealth -= damageToDeal
            Toast.makeText(context, "Вы атаковали бандита, нанеся $damageToDeal урона", Toast.LENGTH_SHORT).show()
            enemyTurn()
        }
        binding.btnDodge.setOnClickListener {
            isDodge = Random.nextInt(100) < 50
            Toast.makeText(context, "Вы попытались уклониться от атаки бандита", Toast.LENGTH_SHORT).show()
            enemyTurn()
        }
        binding.btnHeal.setOnClickListener {
            playerHealth += 20
            Toast.makeText(context, "Вы полечились на 20 HP", Toast.LENGTH_SHORT).show()
            enemyTurn()
        }
    }

    private fun enemyTurn() {
        val banditAction = Random.nextInt(3)
        when (banditAction) {
            0 -> {
                val damageToDeal = if (isDodge) 0 else banditDamage
                playerHealth -= damageToDeal
                binding.banditInfo.text = if (isDodge) {
                    "Вы успешно уклонились от атаки бандита!"
                } else {
                    "Бандит атаковал вас, нанеся $damageToDeal урона!"
                }
            }
            1 -> {
                val enhancedDamage = Random.nextInt(100) < 30
                val banditEffectiveDamage = if (enhancedDamage) banditDamage * 2 else banditDamage
                banditHealth -= banditEffectiveDamage
                binding.banditInfo.text = if (enhancedDamage) {
                    "Бандит использовал усиленную атаку, нанеся вам $banditEffectiveDamage урона!"
                } else {
                    "Бандит блокировал вашу атаку, но вы все равно нанесли $banditEffectiveDamage урона!"
                }
            }
            2 -> {
                banditHealth += 25
                binding.banditInfo.text = "Бандит залечил свои раны на 25 HP!"
            }
        }

        isDodge = false
        isEnhancedAttack = false

        updateUI()

        if (playerHealth <= 0) {
            val bundle = Bundle().apply {
                putInt("Death", 1)
            }
            findNavController().navigate(R.id.action_fightWithBanditsFragment_to_loseFragment, bundle)
        } else if (banditHealth <= 0) {
            showToast("Вы победили бандита!")
        }
    }

    private fun updateUI() {
        binding.health.text = "${playerHealth}HP"
        binding.dmg.text = "${playerDamage}dmg"
        binding.gold.text = "${playerGold}gold"
        binding.banditHealth.text = "$banditHealth"
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}

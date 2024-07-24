package com.example.diplomaproject.presentation.ui.fragment

import android.os.Bundle
import android.provider.Settings.Global.putInt
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.diplomaproject.R
import com.example.diplomaproject.databinding.FragmentFightWithBanditsBinding
import com.example.diplomaproject.data.repositoryImpl.FightRepositoryImpl
import com.example.diplomaproject.domain.repository.FightRepository
import com.example.diplomaproject.presentation.ui.viewmodel.FightWithBanditsVM
import com.example.diplomaproject.presentation.ui.viewmodel.FightWithBanditsVMFactory
import com.example.diplomaproject.domain.useCases.GetBanditStats
import com.example.diplomaproject.domain.useCases.GetPlayerStats
import com.example.diplomaproject.domain.useCases.UpdateBanditStats
import com.example.diplomaproject.domain.useCases.UpdatePlayerStats
import kotlinx.coroutines.launch

class FightWithBanditsFragment : Fragment() {
    private lateinit var binding: FragmentFightWithBanditsBinding

    private val gameRepository = FightRepositoryImpl()

    private val getPlayerStats = GetPlayerStats(gameRepository)
    private val getBanditStats = GetBanditStats(gameRepository)
    private val updatePlayerStats = UpdatePlayerStats(gameRepository)
    private val updateBanditStats = UpdateBanditStats(gameRepository)

    private val viewModel: FightWithBanditsVM by viewModels {
        FightWithBanditsVMFactory(
            gameRepository,
            getBanditStats,
            getPlayerStats,
            updateBanditStats,
            updatePlayerStats
        )
    }

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
        val playerHealth = args.getInt("health", 0)
        val playerDamage = args.getInt("dmg", 0)
        val playerGold = args.getInt("gold", 0)

        viewModel.initPlayer(playerHealth, playerDamage, playerGold)
        viewModel.banditActionMessage.observe(viewLifecycleOwner) { actionMessage ->
            binding.banditInfo.text = actionMessage
        }
        viewModel.toastMessage.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.navigateToLose.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                val bundle = Bundle().apply {
                    putInt("Death", 1)
                }
                findNavController().navigate(R.id.action_fightWithBanditsFragment_to_endScreenFragment,bundle)
            }
        }
        viewModel.navigateToWin.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                val bundle = Bundle().apply {
                    putInt("Win", 1)
                }
                findNavController().navigate(R.id.action_fightWithBanditsFragment_to_endScreenFragment,bundle)
            }
        }

        viewModel.playerStats.observe(viewLifecycleOwner) { stats ->
            binding.health.text = "${stats.health}HP"
            binding.dmg.text = "${stats.damage}dmg"
            binding.gold.text = "${stats.gold}gold"
        }

        viewModel.banditStats.observe(viewLifecycleOwner) { stats ->
            binding.banditHealth.text = "${stats.health}BanditHealth"
        }

        binding.btnAttack.setOnClickListener { viewModel.playerAttack() }
        binding.btnDodge.setOnClickListener { viewModel.playerDodge() }
        binding.btnHeal.setOnClickListener { viewModel.playerHeal() }
    }
    }
package com.example.diplomaproject.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.diplomaproject.data.api_data_source.data_model.BanditStats
import com.example.diplomaproject.data.api_data_source.data_model.PlayerStats
import com.example.diplomaproject.domain.repository.FightRepository
import com.example.diplomaproject.domain.useCases.GetBanditStats
import com.example.diplomaproject.domain.useCases.GetPlayerStats
import com.example.diplomaproject.domain.useCases.UpdateBanditStats
import com.example.diplomaproject.domain.useCases.UpdatePlayerStats

class FightWithBanditsVMFactory(
    private val repository: FightRepository,
    private val getBanditStats: GetBanditStats,
    private val getPlayerStats: GetPlayerStats,
    private val updateBanditStats: UpdateBanditStats,
    private val updatePlayerStats: UpdatePlayerStats
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FightWithBanditsVM::class.java)) {
            return FightWithBanditsVM(repository, getBanditStats, getPlayerStats, updateBanditStats, updatePlayerStats) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
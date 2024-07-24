package com.example.diplomaproject.domain.useCases

import com.example.diplomaproject.domain.repository.FightRepository
import com.example.diplomaproject.data.api_data_source.data_model.BanditStats
import com.example.diplomaproject.data.api_data_source.data_model.PlayerStats

class GetPlayerStats(private val repository: FightRepository) {
    suspend operator fun invoke(): PlayerStats = repository.getPlayerStats()
}

class GetBanditStats(private val repository: FightRepository) {
    suspend operator fun invoke(): BanditStats=repository.getBanditStats()
}

class UpdatePlayerStats(private val repository: FightRepository) {
    suspend operator fun invoke(playerStats: PlayerStats) =
        repository.updatePlayerStats(playerStats)
}

class UpdateBanditStats(private val repository: FightRepository) {
    suspend operator fun invoke(banditStats: BanditStats) =
        repository.updateBanditStats(banditStats)
}
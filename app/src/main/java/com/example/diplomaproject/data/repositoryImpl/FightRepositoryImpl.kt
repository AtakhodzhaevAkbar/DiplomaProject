package com.example.diplomaproject.data.repositoryImpl

import com.example.diplomaproject.data.api_data_source.data_model.BanditStats
import com.example.diplomaproject.data.api_data_source.data_model.PlayerStats
import com.example.diplomaproject.domain.repository.FightRepository

class FightRepositoryImpl : FightRepository {
    override suspend fun getPlayerStats(): PlayerStats {
        return PlayerStats(100, 20, 50)
    }

    override suspend fun getBanditStats(): BanditStats {
        return BanditStats(300, 35)
    }

    override suspend fun updatePlayerStats(playerStats: PlayerStats) {
    }

    override suspend fun updateBanditStats(banditStats: BanditStats) {
    }
}
package com.example.diplomaproject.domain.repository

import com.example.diplomaproject.data.api_data_source.data_model.BanditStats
import com.example.diplomaproject.data.api_data_source.data_model.PlayerStats

interface FightRepository {
    suspend fun getPlayerStats(): PlayerStats
    suspend fun getBanditStats(): BanditStats
    suspend fun updatePlayerStats(playerStats: PlayerStats)
    suspend fun updateBanditStats(banditStats: BanditStats)
}
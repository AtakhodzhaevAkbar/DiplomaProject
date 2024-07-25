package com.example.diplomaproject.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplomaproject.data.Event
import com.example.diplomaproject.data.api_data_source.data_model.BanditStats
import com.example.diplomaproject.data.api_data_source.data_model.PlayerStats
import com.example.diplomaproject.domain.repository.FightRepository
import com.example.diplomaproject.domain.useCases.GetBanditStats
import com.example.diplomaproject.domain.useCases.GetPlayerStats
import com.example.diplomaproject.domain.useCases.UpdateBanditStats
import com.example.diplomaproject.domain.useCases.UpdatePlayerStats
import kotlinx.coroutines.launch
import kotlin.random.Random

class FightWithBanditsVM(
    private val repository: FightRepository,
    private val getBanditStats: GetBanditStats,
    private val getPlayerStats: GetPlayerStats,
    private val updateBanditStats: UpdateBanditStats,
    private val updatePlayerStats: UpdatePlayerStats
) : ViewModel() {
    private val _playerStats = MutableLiveData<PlayerStats>()
    val playerStats: LiveData<PlayerStats> = _playerStats

    private val _banditStats = MutableLiveData<BanditStats>()
    val banditStats: LiveData<BanditStats> = _banditStats

    private var isDodge=false
    private var banditDamage = 35

    private val _banditActionMessage = MutableLiveData<String>()
    val banditActionMessage: LiveData<String> = _banditActionMessage

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = _toastMessage

    private val _navigateToLose = MutableLiveData<Event<Unit>>()
    val navigateToLose: LiveData<Event<Unit>> = _navigateToLose

    private val _navigateToWin = MutableLiveData<Event<Unit>>()
    val navigateToWin: LiveData<Event<Unit>> = _navigateToWin

    init {
        fetchStats()
    }

    fun initPlayer(health: Int, damage: Int, gold: Int) {
        val newPlayerStats = PlayerStats(health, damage, gold)
        _playerStats.postValue(newPlayerStats)
    }

    private fun fetchStats() {
        viewModelScope.launch {
            try {
                _playerStats.postValue(repository.getPlayerStats())
                _banditStats.postValue(repository.getBanditStats())
            } catch (e: Exception) {

            }
        }
    }

    fun updatePlayerStats(playerStats: PlayerStats) {
        viewModelScope.launch {
            repository.updatePlayerStats(playerStats)
            _playerStats.postValue(playerStats)
        }
    }

    private fun checkPlayerHealth() {
        if (_playerStats.value?.health ?: 0 <= 0) {
            _navigateToLose.postValue(Event(Unit))
        }
    }

    private fun updateBanditStats(banditStats: BanditStats) {
        viewModelScope.launch {
            repository.updateBanditStats(banditStats)
            _banditStats.postValue(banditStats)
        }
    }

    private fun checkBanditHealth() {
        if (_banditStats.value?.health ?: 0 <= 0) {
            _navigateToWin.postValue(Event(Unit))
        }
    }

    fun playerAttack() {
        viewModelScope.launch {
            val currentBanditStats = _banditStats.value ?: return@launch
            val playerCurrentDamage = _playerStats.value?.damage ?: return@launch
            val enhancedAttackChance = 20
            val isEnhancedAttack = Random.nextInt(100) < enhancedAttackChance
            val damageMultiplier = if (isEnhancedAttack) 2 else 1
            val finalDamage = playerCurrentDamage * damageMultiplier
            val updatedBanditStats = currentBanditStats.copy(health = currentBanditStats.health - finalDamage)
            updateBanditStats(updatedBanditStats)
            _banditStats.value = updatedBanditStats
            if (isEnhancedAttack) {
                _toastMessage.postValue(Event("Вы нанесли усиленный удар на $finalDamage урона"))
            } else {
                _toastMessage.postValue(Event("Вы атаковали противника на $finalDamage урона"))
            }
            enemyTurn()
            checkPlayerHealth()
            checkBanditHealth()
        }
    }
    fun playerDodge() {
        viewModelScope.launch {
            isDodge = Random.nextInt(100) < 50
            if(isDodge)
                _toastMessage.postValue(Event("Вы увернулись от атаки"))
            else
                _toastMessage.postValue(Event("Вы не смогли увернуться"))
            enemyTurn()
            checkPlayerHealth()
            checkBanditHealth()
        }
    }

    fun playerHeal() {
        viewModelScope.launch {
            _playerStats.value = _playerStats.value?.let {
                it.copy(health = (it.health + 20).coerceAtMost(100))
            }
            _toastMessage.postValue(Event("Вы восстановили 20 хп"))
            enemyTurn()
            checkPlayerHealth()
            checkBanditHealth()
        }
    }

    private fun enemyTurn() {
        viewModelScope.launch {
            val banditAction = Random.nextInt(3)
            when (banditAction) {
                0 -> {
                    val damageToDeal = if (isDodge) 0 else banditDamage
                    _playerStats.value = _playerStats.value?.copy(health = (_playerStats.value?.health ?: 0) - damageToDeal)
                    _banditActionMessage.postValue("Бандит атаковал вас на $damageToDeal урона")
                }
                1 -> {
                    val enhancedDamage = Random.nextInt(100) < 30
                    val banditEffectiveDamage = if (enhancedDamage) banditDamage * 2 else banditDamage
                    _banditStats.value = _banditStats.value?.copy(health = (_banditStats.value?.health ?: 0) - banditEffectiveDamage)
                    _banditActionMessage.postValue("Бандит использовал критическую атаку и нанес $banditEffectiveDamage урона")
                }
                2 -> {
                    _banditStats.value = _banditStats.value?.copy(health = (_banditStats.value?.health ?: 0) + 25)
                    _banditActionMessage.postValue("Бандит полечился на 25хп")
                }
            }
            checkPlayerHealth()
            checkBanditHealth()
            isDodge = false
        }
    }

}
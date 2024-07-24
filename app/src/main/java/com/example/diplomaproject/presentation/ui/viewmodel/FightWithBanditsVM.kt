package com.example.diplomaproject.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FightWithBanditsVM : ViewModel() {
    private val _playerHealth = MutableLiveData<Int>()
    val playerHealth: LiveData<Int> get() = _playerHealth

    private val _playerDamage = MutableLiveData<Int>()
    val playerDamage: LiveData<Int> get() = _playerDamage

    private val _playerGold = MutableLiveData<Int>()
    val playerGold: LiveData<Int> get() = _playerGold

    private val _banditHealth = MutableLiveData<Int>()
    val banditHealth: LiveData<Int> get() = _banditHealth

    private val _battleStatus = MutableLiveData<String>()
    val battleStatus: LiveData<String> get() = _battleStatus

    init {
        _playerHealth.value = 100
        _playerDamage.value = 20
        _playerGold.value = 50
        _banditHealth.value = 300
    }

    fun attack() {
        // Логика атаки игрока
    }

    fun dodge() {
        // Логика уклонения игрока
    }

    fun heal() {
        // Логика лечения игрока
    }

    fun enemyTurn() {
        // Логика хода врага
    }
}

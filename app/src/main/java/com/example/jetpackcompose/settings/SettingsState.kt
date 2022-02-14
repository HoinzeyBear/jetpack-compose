package com.example.jetpackcompose.settings

import com.example.jetpackcompose.Theme

data class SettingsState(
    val notificationsEnabled: Boolean = false,
    val hintsEnabled: Boolean = false,
    val marketingOption: MarketingOption =
        MarketingOption.ALLOWED,
    val themeOption: Theme = Theme.SYSTEM
)
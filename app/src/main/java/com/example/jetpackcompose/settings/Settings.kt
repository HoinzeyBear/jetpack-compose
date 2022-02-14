package com.example.jetpackcompose.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.*
import com.example.jetpackcompose.R

@Composable
fun Settings() {
    val viewModel: SettingsViewModel = viewModel()

    MaterialTheme {
      val state = viewModel.uiState.collectAsState().value
        SettingsList(modifier = Modifier.fillMaxSize(),
            state = state,
            toggleNotificationSetting = viewModel::toggleNotificationSettings,
            onShowHintsToggled = viewModel::toggleHintSettings,
            setMarketingOption = viewModel::setMarketingSettings,
            setSelectedTheme = viewModel::setTheme)
    }
}

@Composable
fun SettingsList(modifier: Modifier,
                 state: SettingsState,
                 toggleNotificationSetting: () -> Unit,
                 onShowHintsToggled: () -> Unit,
                 setMarketingOption: (option: MarketingOption) -> Unit,
                 setSelectedTheme: (theme: Theme) -> Unit){
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.surface,
            contentPadding = PaddingValues(start = 12.dp)) {
//            Row {   todo this was kind of missed in the tutorial
                Icon(tint = MaterialTheme.colors.onSurface,
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.go_back))
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = stringResource(id = R.string.settings_title),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 18.sp)
//            }
        }

        NotificationSettings(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.setting_enable_notifications),
            checked = state.notificationsEnabled,
            onToggleNotificationSettings = toggleNotificationSetting)

        Divider()

        HintSettingsItem(modifier = modifier.fillMaxWidth(),
        title = stringResource(id = R.string.setting_show_hints),
        checked = state.hintsEnabled,
        onShowHintsToggled = onShowHintsToggled)

        Divider()

        ManageSubscriptionSettingItem(modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.setting_manage_subscription),
            onSettingClicked = {})

        SectionSpacer(modifier = Modifier.fillMaxWidth())

        MarketingSettingItem(
            modifier = Modifier.fillMaxWidth(),
            selectedOption = state.marketingOption,
            onOptionSelected = setMarketingOption)

        Divider()

        ThemeSettingItem(
            modifier = Modifier.fillMaxWidth(),
            selectedTheme = state.themeOption,
            onOptionSelected = setSelectedTheme)

        SectionSpacer(modifier = Modifier.fillMaxWidth())

        AppVersionSettingItem(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.setting_app_version_title),
            description = stringResource(id = R.string.setting_app_version)
        )
        Divider()
    }
}
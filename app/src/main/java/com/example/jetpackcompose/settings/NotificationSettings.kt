package com.example.jetpackcompose.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.R

@Composable
fun NotificationSettings(
    modifier: Modifier = Modifier,
    title: String,
    checked: Boolean,
    onToggleNotificationSettings: () -> Unit) {
    val notificationsEnabledState = if (checked) {
        stringResource(id = R.string.notifications_enabled)
    } else stringResource(id = R.string.notifications_disabled)
    SettingItem(modifier = modifier) {
        Row(modifier = Modifier
            .padding(horizontal = 16.dp)
            .toggleable(
                value = checked,
                onValueChange = { onToggleNotificationSettings() },
                role = Role.Switch
            )
            .semantics { stateDescription = notificationsEnabledState },
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, modifier = Modifier.weight(1f))
            Switch(
                checked = checked,
                onCheckedChange = null
            )
        }
    }
}
package com.example.jetpackcompose.homescreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import com.example.jetpackcompose.R
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock

class TopBarTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun ChildDestinationTopBar_Title_Displayed() {
        val title = Destination.Feed.path
        composeTestRule.setContent {
            ChildDestinationTopBar(
                title = title,
                onNavigateUp = {})
        }

        composeTestRule.onNodeWithText(
            title
        ).assertIsDisplayed()
    }

    @Test
    fun Navigation_Icon_Displayed() {
        composeTestRule.setContent {
            ChildDestinationTopBar(
                onNavigateUp = { },
                title = Destination.Feed.path)
        }

        composeTestRule.onNodeWithContentDescription(
            InstrumentationRegistry.getInstrumentation().context
                .getString(R.string.cd_navigate_up)
        ).assertIsDisplayed()
    }

    @Test
    fun Navigation_Icon_Triggers_Callback() {
        val onUpClicked: () -> Unit = mock()
        composeTestRule.setContent {
            ChildDestinationTopBar(
                onNavigateUp = onUpClicked,
                title = Destination.Feed.path)
        }
        composeTestRule.onNodeWithContentDescription(
            InstrumentationRegistry.getInstrumentation().context
                .getString(R.string.cd_navigate_up)
        ).performClick()
        verify(onUpClicked).invoke()
    }

    @Test
    fun RootDestinationTopBar_Title_Displayed() {
        composeTestRule.setContent {
            RootDestinationTopBar(
                currentDestination = Destination.Home,
                openDrawer = { },
                showSnackbar = { })
        }
        composeTestRule.onNodeWithText(
            Destination.Home.path
        ).assertIsDisplayed()
    }

    @Test
    fun RootDestinationTopBar_Menu_Icon_Displayed() {
        composeTestRule.setContent {
            RootDestinationTopBar(
                currentDestination = Destination.Home,
                openDrawer = { },
                showSnackbar = { })
        }
        composeTestRule.onNodeWithContentDescription(
            InstrumentationRegistry.getInstrumentation().context
                .getString(R.string.cd_open_menu)
        ).assertIsDisplayed()
    }

    @Test
    fun RootDestinationTopBar_Menu_Icon_Triggers_Callback() {
        val openDrawer: () -> Unit = mock()
        composeTestRule.setContent {
            RootDestinationTopBar(
                currentDestination = Destination.Home,
                openDrawer = openDrawer,
                showSnackbar = { })
        }
        composeTestRule.onNodeWithContentDescription(
            InstrumentationRegistry.getInstrumentation().context
                .getString(R.string.cd_open_menu)
        ).performClick()
        verify(openDrawer).invoke()
    }

    @Test
    fun RootDestinationTopBar_Info_Icon_Displayed() {
        composeTestRule.setContent {
            RootDestinationTopBar(
                currentDestination = Destination.Contacts,
                openDrawer = { },
                showSnackbar = { })
        }
        composeTestRule.onNodeWithContentDescription(
            InstrumentationRegistry.getInstrumentation().context
                .getString(R.string.cd_more_information)
        ).assertIsDisplayed()
    }

    @Test
    fun RootDestinationTopBar_Info_Icon_Displayed_Never_Displayed_For_Feed() {
        composeTestRule.setContent {
            RootDestinationTopBar(
                currentDestination = Destination.Feed,
                openDrawer = { },
                showSnackbar = { })
        }
        composeTestRule.onNodeWithContentDescription(
            InstrumentationRegistry.getInstrumentation().context
                .getString(R.string.cd_more_information)
        ).assertDoesNotExist()
    }

    @Test
    fun RootDestinationTopBar_Info_Icon_Triggers_Callback() {
        val showSnackbar: (message: String) -> Unit = mock()
        composeTestRule.setContent {
            RootDestinationTopBar(
                currentDestination = Destination.Home,
                openDrawer = { },
                showSnackbar = showSnackbar)
        }
        composeTestRule.onNodeWithContentDescription(
            InstrumentationRegistry.getInstrumentation().context
                .getString(R.string.cd_more_information))
            .performClick()

        verify(showSnackbar).invoke(
            InstrumentationRegistry.getInstrumentation().context
                .getString(R.string.not_available_yet)
        )
    }
}
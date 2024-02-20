package com.disney.interview

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.disney.framework.http.responses.models.Results
import com.disney.framework.http.responses.models.Thumbnail
import com.disney.interview.ui.comics.ScreenSplitBottom
import com.disney.interview.ui.comics.ScreenSplitTop
import com.disney.interview.ui.utils.TestTags
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun composeUnitTest() {

        composeTestRule.setContent {
            val results = Results(
                title = TestTags.TEST_TAG_TITLE,
                description = TestTags.TEST_TAG_DESCRIPTION,
                thumbnail = Thumbnail(
                    path = TestTags.TEST_TAG_PHOTO_URL
                )
            )
            Column {
                ScreenSplitTop(results = results)
                ScreenSplitBottom(results = results)
            }
        }

        // find the controlling UI component
        val textFieldTitle = composeTestRule.onNodeWithText("title")
        assertTrue(textFieldTitle.isDisplayed())
        val textFieldDescription = composeTestRule.onNodeWithText("description")
        assertTrue(textFieldDescription.isDisplayed())
    }
}

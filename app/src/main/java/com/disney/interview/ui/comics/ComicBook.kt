package com.disney.interview.ui.comics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.disney.framework.http.responses.models.Results
import com.disney.framework.http.responses.models.Thumbnail
import com.disney.interview.R
import com.disney.interview.data.MainScreenViewModel
import com.disney.interview.data.ViewState
import com.disney.interview.ui.common.DescriptionText
import com.disney.interview.ui.common.ImageRefactor
import com.disney.interview.ui.common.StandardText
import com.disney.interview.ui.common.TitleText
import com.disney.interview.ui.components.BottomNavigationViews
import com.disney.interview.ui.components.ButtonWithIcon
import com.disney.interview.ui.enum.InteractiveStateOptions
import com.disney.interview.ui.theme.DisneyInterviewTheme
import com.disney.interview.ui.theme.Purple40
import com.disney.interview.ui.utils.TestTags.TEST_TAG_DESCRIPTION
import com.disney.interview.ui.utils.TestTags.TEST_TAG_PHOTO_URL
import com.disney.interview.ui.utils.TestTags.TEST_TAG_TITLE
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun ComicBookDetailsPreview() {
    DisneyInterviewTheme {
        val results = Results(
            title = TEST_TAG_TITLE,
            description = TEST_TAG_DESCRIPTION,
            thumbnail = Thumbnail(
                path = TEST_TAG_PHOTO_URL
            )
        )
        Column {
            ScreenSplitTop(results = results)
            ScreenSplitBottom(results = results)
        }
    }
}

@Composable
fun ComicBookDetails(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel
) {
    // Collects values from this StateFlow and represents its latest value via State in a
    // lifecycle-aware manner
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    when (viewState) {
        is ViewState.ContentAvailable -> {
            // show content
            val results = (viewState as ViewState.ContentAvailable)
                .getComicByIdResponse?.data?.results?.get(0)

            Column(
                modifier = modifier
            ) {
                ScreenSplitTop(results = results)
                ScreenSplitBottom(results = results)
            }
        }

        else -> {
            // loading UI
            // TODO in future updates could add loader with refreshing
        }
    }
}

@Composable
fun ScreenSplitTop(
    modifier: Modifier = Modifier,
    results: Results?
) {
    Column(
        modifier = modifier
            .fillMaxHeight(0.4f)
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageRefactor(url = results?.thumbnail?.path)

            Column(
                modifier = modifier
                    .padding(5.dp, 5.dp, 10.dp, 5.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = modifier
                        .background(Purple40)
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxWidth(1f)
                        .wrapContentSize(Alignment.Center)
                        .align(alignment = Alignment.CenterHorizontally)
                ) {
                    StandardText(
                        modifier = modifier
                            .fillMaxWidth(1f)
                            .wrapContentHeight()
                            .align(Alignment.Center)
                            .padding(20.dp),
                        value = stringResource(R.string.read_now).uppercase(Locale.US),
                        textStyle = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
                ButtonWithIcon(
                    modifier = modifier,
                    interactiveStateOptions = InteractiveStateOptions.MARK_AS_READ
                )
                ButtonWithIcon(
                    modifier = modifier,
                    interactiveStateOptions = InteractiveStateOptions.ADD_TO_LIBRARY
                )
                ButtonWithIcon(
                    modifier = modifier,
                    interactiveStateOptions = InteractiveStateOptions.READ_OFFLINE
                )
            }
        }
    }
}

@Composable
fun ScreenSplitBottom(
    modifier: Modifier = Modifier,
    results: Results?
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .background(Color.White),
    ) {
        TitleText(title = results?.title.orEmpty())
        Divider()
        DescriptionText(description = results?.description.orEmpty())
        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationViews()
    }
}

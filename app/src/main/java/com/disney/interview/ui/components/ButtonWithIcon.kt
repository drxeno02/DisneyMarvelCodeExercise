package com.disney.interview.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.disney.interview.R
import com.disney.interview.ui.common.StandardText
import com.disney.interview.ui.enum.InteractiveStateOptions
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun ButtonWithIconPreview(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonWithIcon(interactiveStateOptions = InteractiveStateOptions.MARK_AS_READ)
        ButtonWithIcon(interactiveStateOptions = InteractiveStateOptions.ADD_TO_LIBRARY)
        ButtonWithIcon(interactiveStateOptions = InteractiveStateOptions.READ_OFFLINE)
    }
}

@Composable
fun ButtonWithIcon(
    modifier: Modifier = Modifier,
    interactiveStateOptions: InteractiveStateOptions
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.DarkGray)
    ) {
        Row(
            modifier = modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            val id: Int
            val contentDescription: String
            val label: String

            when (interactiveStateOptions) {
                InteractiveStateOptions.ADD_TO_LIBRARY -> {
                    // add to library
                    id = R.drawable.baseline_check_circle_24
                    contentDescription = stringResource(R.string.acc_add_to_library)
                    label = stringResource(R.string.add_to_library).uppercase(Locale.US)
                }

                InteractiveStateOptions.READ_OFFLINE -> {
                    // read offline
                    id = R.drawable.baseline_add_circle_24
                    contentDescription = stringResource(R.string.acc_read_offline)
                    label = stringResource(R.string.read_offline).uppercase(Locale.US)
                }

                else -> {
                    // mark as read
                    id = R.drawable.baseline_file_download_24
                    contentDescription = stringResource(R.string.acc_mark_as_read)
                    label = stringResource(R.string.mark_as_read).uppercase(Locale.US)
                }
            }

            Icon(
                modifier = modifier
                    .padding(1.dp)
                    .size(dimensionResource(id = R.dimen.icon_small)),
                painter = painterResource(id = id),
                tint = Color.White,
                contentDescription = contentDescription
            )
            Divider(
                color = Color.White,
                modifier = Modifier
                    .padding(3.dp, 5.dp, 10.dp, 5.dp)
                    .height(25.dp)
                    .width(1.dp)
            )
            StandardText(
                value = label
            )
        }
    }
}

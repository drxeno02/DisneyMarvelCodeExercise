package com.disney.interview.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun BottomNavigationViews(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.DarkGray),
        verticalAlignment = Alignment.Bottom
    ) {
        PreviousButton()
        NextButton()
    }
}

@Composable
fun PreviousButton(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Left
    ) {
        Icon(
            modifier = modifier.size(dimensionResource(id = R.dimen.icon_small)),
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_left_white_24),
            tint = Color.Gray,
            contentDescription = stringResource(R.string.acc_previous_button)
        )
        StandardText(
            value = stringResource(R.string.previous).uppercase(Locale.US),
            textColor = Color.Gray
        )
    }
}

@Composable
fun NextButton(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Right
    ) {
        StandardText(
            value = stringResource(R.string.next).uppercase(Locale.US)
        )
        Icon(
            modifier = modifier
                .padding()
                .size(dimensionResource(id = R.dimen.icon_small)),
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_white_24),
            tint = Color.White,
            contentDescription = stringResource(R.string.acc_next_button)
        )
    }
}

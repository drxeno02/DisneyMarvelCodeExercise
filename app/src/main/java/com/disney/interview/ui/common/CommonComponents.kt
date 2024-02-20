package com.disney.interview.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.disney.interview.R

@Composable
fun StandardText(
    modifier: Modifier = Modifier,
    value: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = Color.White
) {
    Text(
        text = value,
        style = textStyle,
        textAlign = textAlign,
        color = textColor,
        modifier = modifier
    )
}

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    title: String,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = Color.Gray
) {
    Text(
        text = title,
        style = textStyle,
        textAlign = textAlign,
        color = textColor,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun DescriptionText(
    modifier: Modifier = Modifier,
    description: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = Color.Gray
) {
    Text(
        text = description,
        style = textStyle,
        textAlign = textAlign,
        color = textColor,
        modifier = modifier.padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ImageRefactor(
    url: String? = null
) {
    Row(
        modifier = Modifier.padding(PaddingValues(10.dp)),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = url,
            error = painterResource(R.drawable.profile_icon),
            placeholder = painterResource(R.drawable.profile_icon),
            contentDescription = stringResource(R.string.acc_profile_picture),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(150.dp)
                .weight(1f, fill = false)
                .fillMaxHeight()
                .clip(RoundedCornerShape(CornerSize(10.dp)))
        )
    }
}

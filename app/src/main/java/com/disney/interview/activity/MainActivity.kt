package com.disney.interview.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.disney.interview.data.MainScreenViewModel
import com.disney.interview.ui.comics.ComicBookDetails
import com.disney.interview.ui.theme.DisneyInterviewTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = MainScreenViewModel()
        viewModel.getComicById()

        super.onCreate(savedInstanceState)
        setContent {
            DisneyInterviewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ComicBookDetails(viewModel = viewModel)
                }
            }
        }
    }
}

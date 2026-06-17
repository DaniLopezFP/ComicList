package com.example.mycomiclist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycomiclist.navigation.NavigationWrapper
import com.example.mycomiclist.ui.theme.MyComicListTheme
import com.example.mycomiclist.ui.theme.azul1
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            MyComicListTheme {
                FirebaseApp.initializeApp(this)

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                        //.padding(innerPadding),
                    color = colorScheme.background
                ) {
                    NavigationWrapper()
                }

            }
        }
    }
}


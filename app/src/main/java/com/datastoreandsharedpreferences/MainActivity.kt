package com.datastoreandsharedpreferences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.datastoreandsharedpreferences.ui.theme.DataStoreAndSharedPreferencesTheme

class MainActivity : ComponentActivity() {

    val dataStoreViewModel by viewModels<DataStoreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreAndSharedPreferencesTheme(darkTheme = dataStoreViewModel.darkThemeDataStore.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DataStore(dataStoreViewModel = dataStoreViewModel)
                }
            }
        }
    }
}

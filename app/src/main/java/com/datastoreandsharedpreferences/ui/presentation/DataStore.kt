package com.datastoreandsharedpreferences

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DataStore(dataStoreViewModel: DataStoreViewModel) {
    var key = remember { mutableStateOf("") }
    var value = remember { mutableStateOf("") }
    var keyGet = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(" ${dataStoreViewModel.darkThemeSharedPrefs.value}")
        Button(onClick = { dataStoreViewModel.toggleThemeSharedPrefs() }) {
            Text("Toggle theme SharedPrefs")
        }

        Spacer(modifier = Modifier.padding(10.dp))
        Text(" ${dataStoreViewModel.darkThemeDataStore.value}")
        Button(onClick = { dataStoreViewModel.toggleThemeDataStore() }) {
            Text("Toggle theme DataStore")
        }
        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            TextField(
                value = key.value,
                onValueChange = { key.value = it },
                label = { Text("key") },
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .padding(2.dp),
                maxLines = 1,
                singleLine = true
            )
            TextField(
                value = value.value,
                onValueChange = { value.value = it },
                label = { Text("value") },
                maxLines = 1,
                singleLine = true
            )
        }
        Button(onClick = {
            dataStoreViewModel.saveKeyValue(key.value, value.value)
            key.value = ""
            value.value = ""
        }) {
            Text("save key-value")
        }
        Spacer(modifier = Modifier.padding(10.dp))

        TextField(
            value = keyGet.value,
            onValueChange = { keyGet.value = it },
            label = { Text("key") },
            maxLines = 1,
            singleLine = true
        )
        Button(onClick = { dataStoreViewModel.loadKeyValue(keyGet.value) }) {
            Text("Load key-value")
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Text(dataStoreViewModel.loadedValue.value)

        Spacer(modifier = Modifier.padding(10.dp))
        Text("key : dog  /  value : ${dataStoreViewModel.dogFlow.collectAsState(initial = "").value}")
    }
}
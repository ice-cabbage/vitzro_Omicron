package com.stonetranslator.omicron

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.stonetranslator.omicron.ui.theme.OmicronTheme

val demandTopics = arrayListOf(
    arrayOf("MI/MMTR/TotVAh/actVal/High", "VAh"),
    arrayOf("MI/MMTR/TotVAh/actVal/Low", "VAh"),
    arrayOf("MI/MMTR/TotWh/actVal/High", "Wh"),
    arrayOf("MI/MMTR/TotWh/actVal/Low", "Wh"),
    arrayOf("MI/MMTR/TotVArh/actVal/High", "VARh"),
    arrayOf("MI/MMTR/TotVArh/actVal/Low", "VARh"),
    arrayOf("MI/MMTR/LgTotVArh/actVal/High", "VARh"),
    arrayOf("MI/MMTR/LgTotVArh/actVal/Low", "VARh"),
    arrayOf("MI/MMTR/VAh/phsA/actVal/High", "VAh"),
    arrayOf("MI/MMTR/VAh/phsA/actVal/Low", "VAh"),
    arrayOf("MI/MMTR/VAh/phsB/actVal/High", "VAh"),
    arrayOf("MI/MMTR/VAh/phsB/actVal/Low", "VAh"),
    arrayOf("MI/MMTR/VAh/phsC/actVal/High", "VAh"),
    arrayOf("MI/MMTR/VAh/phsC/actVal/Low", "VAh"),
    arrayOf("MI/MMTR/Wh/phsA/actVal/High", "Wh"),
    arrayOf("MI/MMTR/Wh/phsA/actVal/Low", "Wh"),
    arrayOf("MI/MMTR/Wh/phsB/actVal/High", "Wh"),
    arrayOf("MI/MMTR/Wh/phsB/actVal/Low", "Wh"),
    arrayOf("MI/MMTR/Wh/phsC/actVal/High", "Wh"),
    arrayOf("MI/MMTR/Wh/phsC/actVal/Low", "Wh"),
    arrayOf("MI/MMTR/VArh/phsA/actVal/High", "VARh"),
    arrayOf("MI/MMTR/VArh/phsA/actVal/Low", "VARh"),
    arrayOf("MI/MMTR/VArh/phsB/actVal/High", "VARh"),
    arrayOf("MI/MMTR/VArh/phsB/actVal/Low", "VARh"),
    arrayOf("MI/MMTR/VArh/phsC/actVal/High", "VARh"),
    arrayOf("MI/MMTR/VArh/phsC/actVal/Low", "VARh"),
    arrayOf("MI/MMTR/LgVArh/phsA/actVal/High", "VARh"),
    arrayOf("MI/MMTR/LgVArh/phsA/actVal/Low", "VARh"),
    arrayOf("MI/MMTR/LgVArh/phsB/actVal/High", "VARh"),
    arrayOf("MI/MMTR/LgVArh/phsB/actVal/Low", "VARh"),
    arrayOf("MI/MMTR/LgVArh/phsC/actVal/High", "VARh"),
    arrayOf("MI/MMTR/LgVArh/phsC/actVal/Low", "VARh"),
    arrayOf("MI/MMTR/DmdAh/phsA/val/mag", "Ah"),
    arrayOf("MI/MMTR/DmdAh/phsA/val/angle", "Deg"),
    arrayOf("MI/MMTR/DmdAh/phsB/val/mag", "Ah"),
    arrayOf("MI/MMTR/DmdAh/phsB/val/angle", "Deg"),
    arrayOf("MI/MMTR/DmdAh/phsC/val/mag", "Ah"),
    arrayOf("MI/MMTR/DmdAh/phsC/val/angle", "Deg"),
    arrayOf("MI/MMTR/DmdAh/neut/val/mag", "Ah"),
    arrayOf("MI/MMTR/DmdAh/neut/val/angle", "Ah"),
    arrayOf("MI/MMTR/DmdVAh/mag", "VAh"),
    arrayOf("MI/MMTR/DmdWh/mag", "Wh"),
    arrayOf("MI/MMTR/DmdWh/mag", "Wh")
)

@Composable
fun DemandScreen() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxSize()) {
        SearchView(textState)
        TopicList(demandTopics, textState, focusManager)
    }
}

@Preview
@Composable
fun DemandScreenPreview() {
    OmicronTheme() {
        DemandScreen()
    }
}
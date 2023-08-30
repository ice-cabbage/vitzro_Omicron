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

val reverseTopics = arrayListOf(
    arrayOf("MI/MMTR_Reverse/TotVAh/actVal/High", "VAh"),
    arrayOf("MI/MMTR_Reverse/TotVAh/actVal/Low", "VAh"),
    arrayOf("MI/MMTR_Reverse/TotWh/actVal/High", "Wh"),
    arrayOf("MI/MMTR_Reverse/TotWh/actVal/Low", "Wh"),
    arrayOf("MI/MMTR_Reverse/TotVArh/actVal/High", "VARh"),
    arrayOf("MI/MMTR_Reverse/TotVArh/actVal/Low", "VARh"),
    arrayOf("MI/MMTR_Reverse/LgTotVArh/actVal/High", "VARh"),
    arrayOf("MI/MMTR_Reverse/LgTotVArh/actVal/Low", "VARh"),
    arrayOf("MI/MMTR_Reverse/VAh/phsA/actVal/High", "VAh"),
    arrayOf("MI/MMTR_Reverse/VAh/phsA/actVal/Low", "VAh"),
    arrayOf("MI/MMTR_Reverse/VAh/phsB/actVal/High", "VAh"),
    arrayOf("MI/MMTR_Reverse/VAh/phsB/actVal/Low", "VAh"),
    arrayOf("MI/MMTR_Reverse/VAh/phsC/actVal/High", "VAh"),
    arrayOf("MI/MMTR_Reverse/VAh/phsC/actVal/Low", "VAh"),
    arrayOf("MI/MMTR_Reverse/Wh/phsA/actVal/High", "Wh"),
    arrayOf("MI/MMTR_Reverse/Wh/phsA/actVal/Low", "Wh"),
    arrayOf("MI/MMTR_Reverse/Wh/phsB/actVal/High", "Wh"),
    arrayOf("MI/MMTR_Reverse/Wh/phsB/actVal/Low", "Wh"),
    arrayOf("MI/MMTR_Reverse/Wh/phsC/actVal/High", "Wh"),
    arrayOf("MI/MMTR_Reverse/Wh/phsC/actVal/Low", "Wh"),
    arrayOf("MI/MMTR_Reverse/VArh/phsA/actVal/High", "VARh"),
    arrayOf("MI/MMTR_Reverse/VArh/phsA/actVal/Low", "VARh"),
    arrayOf("MI/MMTR_Reverse/VArh/phsB/actVal/High", "VARh"),
    arrayOf("MI/MMTR_Reverse/VArh/phsB/actVal/Low", "VARh"),
    arrayOf("MI/MMTR_Reverse/VArh/phsC/actVal/High", "VARh"),
    arrayOf("MI/MMTR_Reverse/VArh/phsC/actVal/Low", "VARh"),
    arrayOf("MI/MMTR_Reverse/LgVArh/phsA/actVal/High", "VARh"),
    arrayOf("MI/MMTR_Reverse/LgVArh/phsA/actVal/Low", "VARh"),
    arrayOf("MI/MMTR_Reverse/LgVArh/phsB/actVal/High", "VARh"),
    arrayOf("MI/MMTR_Reverse/LgVArh/phsB/actVal/Low", "VARh"),
    arrayOf("MI/MMTR_Reverse/LgVArh/phsC/actVal/High", "VARh"),
    arrayOf("MI/MMTR_Reverse/LgVArh/phsC/actVal/Low", "VARh"),
    arrayOf("MI/MMTR_Reverse/DmdAh/phsA/val/mag", "Ah"),
    arrayOf("MI/MMTR_Reverse/DmdAh/phsA/val/angle", "Deg"),
    arrayOf("MI/MMTR_Reverse/DmdAh/phsB/val/mag", "Ah"),
    arrayOf("MI/MMTR_Reverse/DmdAh/phsB/val/angle", "Deg"),
    arrayOf("MI/MMTR_Reverse/DmdAh/phsC/val/mag", "Ah"),
    arrayOf("MI/MMTR_Reverse/DmdAh/phsC/val/angle", "Deg"),
    arrayOf("MI/MMTR_Reverse/DmdAh/neut/val/mag", "Ah"),
    arrayOf("MI/MMTR_Reverse/DmdAh/neut/val/angle", "Ah"),
    arrayOf("MI/MMTR_Reverse/DmdVAh/mag", "VAh"),
    arrayOf("MI/MMTR_Reverse/DmdWh/mag", "Wh"),
    arrayOf("MI/MMTR_Reverse/DmdWh/mag", "Wh")
)

@Composable
fun ReverseScreen() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxSize()) {
        SearchView(textState)
        TopicList(reverseTopics, textState, focusManager)
    }
}

@Preview
@Composable
fun ReverseScreenPreview() {
    OmicronTheme() {
        ReverseScreen()
    }
}
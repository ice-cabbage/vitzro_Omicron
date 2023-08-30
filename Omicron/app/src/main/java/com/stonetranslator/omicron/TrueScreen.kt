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

var trueTopics = arrayListOf(
    arrayOf("MI/MMXUT/TotW/mag", "W"),
    arrayOf("MI/MMXUT/TotVAr/mag", "VAR"),
    arrayOf("MI/MMXUT/TotVA/mag", "VA"),
    arrayOf("MI/MMXUT/TotPF/mag", "W/VA"),
    arrayOf("MI/MMXUT/Hz/mag", "Hz"),
    arrayOf("MI/MMXUT/PPV/phsAB/val/mag", "V"),
    arrayOf("MI/MMXUT/PPV/phsAB/val/ang", "Deg"),
    arrayOf("MI/MMXUT/PPV/phsBC/val/mag", "V"),
    arrayOf("MI/MMXUT/PPV/phsBC/val/ang", "Deg"),
    arrayOf("MI/MMXUT/PPV/phsCA/val/mag", "V"),
    arrayOf("MI/MMXUT/PPV/phsCA/val/ang", "Deg"),
    arrayOf("MI/MMXUT/PhV/phsA/val/mag", "V"),
    arrayOf("MI/MMXUT/PhV/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXUT/PhV/phsB/val/mag", "V"),
    arrayOf("MI/MMXUT/PhV/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXUT/PhV/phsC/val/mag", "V"),
    arrayOf("MI/MMXUT/PhV/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXUT/PhV/neut/val/mag", "V"),
    arrayOf("MI/MMXUT/PhV/neut/val/ang", "Deg"),
    arrayOf("MI/MMXUT/A/phsA/val/mag", "A"),
    arrayOf("MI/MMXUT/A/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXUT/A/phsB/val/mag", "A"),
    arrayOf("MI/MMXUT/A/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXUT/A/phsC/val/mag", "A"),
    arrayOf("MI/MMXUT/A/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXUT/A/neut/val/mag", "A"),
    arrayOf("MI/MMXUT/A/neut/val/ang", "Deg"),
    arrayOf("MI/MMXUT/W/phsA/val/mag", "W"),
    arrayOf("MI/MMXUT/W/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXUT/W/phsB/val/mag", "W"),
    arrayOf("MI/MMXUT/W/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXUT/W/phsC/val/mag", "W"),
    arrayOf("MI/MMXUT/W/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXUT/W/neut/val/mag", "W"),
    arrayOf("MI/MMXUT/W/neut/val/ang", "Deg"),
    arrayOf("MI/MMXUT/VAr/phsA/val/mag", "VAR"),
    arrayOf("MI/MMXUT/VAr/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXUT/VAr/phsB/val/mag", "VAR"),
    arrayOf("MI/MMXUT/VAr/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXUT/VAr/phsC/val/mag", "VAR"),
    arrayOf("MI/MMXUT/VAr/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXUT/VAr/neut/val/mag", "VAR"),
    arrayOf("MI/MMXUT/VAr/neut/val/ang", "Deg"),
    arrayOf("MI/MMXUT/VA/phsA/val/mag", "VA"),
    arrayOf("MI/MMXUT/VA/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXUT/VA/phsB/val/mag", "VA"),
    arrayOf("MI/MMXUT/VA/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXUT/VA/phsC/val/mag", "VA"),
    arrayOf("MI/MMXUT/VA/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXUT/VA/neut/val/mag", "VA"),
    arrayOf("MI/MMXUT/VA/neut/val/ang", "Deg"),
    arrayOf("MI/MMXUT/PF/phsA/val/mag", "W/VA"),
    arrayOf("MI/MMXUT/PF/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXUT/PF/phsB/val/mag", "W/VA"),
    arrayOf("MI/MMXUT/PF/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXUT/PF/phsC/val/mag", "W/VA"),
    arrayOf("MI/MMXUT/PF/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXUT/PF/neut/val/mag", "W/VA"),
    arrayOf("MI/MMXUT/PF/neut/val/ang", "Deg"),
    arrayOf("MI/MMXUT/Z/phsA/val/mag", "Ohm"),
    arrayOf("MI/MMXUT/Z/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXUT/Z/phsB/val/mag", "Ohm"),
    arrayOf("MI/MMXUT/Z/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXUT/Z/phsC/val/mag", "Ohm"),
    arrayOf("MI/MMXUT/Z/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXUT/Z/neut/val/mag", "Ohm"),
    arrayOf("MI/MMXUT/Z/neut/val/ang", "Deg")
)

@Composable
fun TrueScreen() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxSize()) {
        SearchView(textState)
        TopicList(trueTopics, textState, focusManager)
    }
}

@Preview
@Composable
fun TrueScreenPreview() {
    OmicronTheme() {
        TrueScreen()
    }
}
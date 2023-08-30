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

var sequentialTopics = arrayListOf(
    arrayOf("MI/MSQI/SeqA/c1/mag", "A"),
    arrayOf("MI/MSQI/SeqA/c1/real", "A"),
    arrayOf("MI/MSQI/SeqA/c1/imag", "A"),
    arrayOf("MI/MSQI/SeqV/c1/mag", "V"),
    arrayOf("MI/MSQI/SeqV/c1/real", "V"),
    arrayOf("MI/MSQI/SeqV/c1/imag", "V"),
    arrayOf("MI/MSQI/DQ0Seq/c1/mag", ""),
    arrayOf("MI/MSQI/DQ0Seq/c1/real", ""),
    arrayOf("MI/MSQI/DQ0Seq/c1/imag", ""),
    arrayOf("MI/MSQI/phsA/val/mag", "V"),
    arrayOf("MI/MSQI/phsA/val/angle", "Deg"),
    arrayOf("MI/MSQI/phsB/val/mag", "V"),
    arrayOf("MI/MSQI/phsB/val/angle", "Deg"),
    arrayOf("MI/MSQI/phsC/val/mag", "V"),
    arrayOf("MI/MSQI/phsC/val/angle", "Deg"),
    arrayOf("MI/MSQI/neut/val/mag", "V"),
    arrayOf("MI/MSQI/neut/val/angle", "Deg"),
    arrayOf("MI/MSQI/ImbNgA/mag", "A"),
    arrayOf("MI/MSQI/ImbNgV/mag", "V"),
    arrayOf("MI/MSQI/phsAB/val/mag", "V"),
    arrayOf("MI/MSQI/phsAB/val/angle", "Deg"),
    arrayOf("MI/MSQI/phsBC/val/mag", "V"),
    arrayOf("MI/MSQI/phsBC/val/angle", "Deg"),
    arrayOf("MI/MSQI/phsCA/val/mag", "V"),
    arrayOf("MI/MSQI/phsCA/val/angle", "Deg"),
    arrayOf("MI/MSQI/ImbA/mag", "A"),
    arrayOf("MI/MSQI/ImbV/mag", "V"),
    arrayOf("MI/MSQI/ImbZroA/mag", "A"),
    arrayOf("MI/MSQI/ImbZroV/mag", "V")
)

@Composable
fun SequentialScreen() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxSize()) {
        SearchView(textState)
        TopicList(sequentialTopics, textState, focusManager)
    }
}

@Preview
@Composable
fun SequentialScreenPreview() {
    OmicronTheme {
        SequentialScreen()
    }
}


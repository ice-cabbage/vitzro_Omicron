package com.stonetranslator.omicron

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stonetranslator.omicron.ui.theme.OmicronTheme
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import java.util.Locale

var topics = HashMap<String, Any>()

var homeTopics = arrayListOf(
    arrayOf("MI/MMXU/TotW/mag", "W"),
    arrayOf("MI/MMXU/TotVAr/mag", "VAR"),
    arrayOf("MI/MMXU/TotVA/mag", "VA"),
    arrayOf("MI/MMXU/TotPF/mag", "W/VA"),
    arrayOf("MI/MMXU/Hz/mag", "Hz"),
    arrayOf("MI/MMXU/PPV/phsAB/val/mag", "V"),
    arrayOf("MI/MMXU/PPV/phsAB/val/ang", "Deg"),
    arrayOf("MI/MMXU/PPV/phsBC/val/mag", "V"),
    arrayOf("MI/MMXU/PPV/phsBC/val/ang", "Deg"),
    arrayOf("MI/MMXU/PPV/phsCA/val/mag", "V"),
    arrayOf("MI/MMXU/PPV/phsCA/val/ang", "Deg"),
    arrayOf("MI/MMXU/PhV/phsA/val/mag", "V"),
    arrayOf("MI/MMXU/PhV/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXU/PhV/phsB/val/mag", "V"),
    arrayOf("MI/MMXU/PhV/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXU/PhV/phsC/val/mag", "V"),
    arrayOf("MI/MMXU/PhV/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXU/PhV/neut/val/mag", "V"),
    arrayOf("MI/MMXU/PhV/neut/val/ang", "Deg"),
    arrayOf("MI/MMXU/A/phsA/val/mag", "A"),
    arrayOf("MI/MMXU/A/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXU/A/phsB/val/mag", "A"),
    arrayOf("MI/MMXU/A/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXU/A/phsC/val/mag", "A"),
    arrayOf("MI/MMXU/A/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXU/A/neut/val/mag", "A"),
    arrayOf("MI/MMXU/A/neut/val/ang", "Deg"),
    arrayOf("MI/MMXU/W/phsA/val/mag", "W"),
    arrayOf("MI/MMXU/W/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXU/W/phsB/val/mag", "W"),
    arrayOf("MI/MMXU/W/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXU/W/phsC/val/mag", "W"),
    arrayOf("MI/MMXU/W/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXU/W/neut/val/mag", "W"),
    arrayOf("MI/MMXU/W/neut/val/ang", "Deg"),
    arrayOf("MI/MMXU/VAr/phsA/val/mag", "VAR"),
    arrayOf("MI/MMXU/VAr/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXU/VAr/phsB/val/mag", "VAR"),
    arrayOf("MI/MMXU/VAr/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXU/VAr/phsC/val/mag", "VAR"),
    arrayOf("MI/MMXU/VAr/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXU/VAr/neut/val/mag", "VAR"),
    arrayOf("MI/MMXU/VAr/neut/val/ang", "Deg"),
    arrayOf("MI/MMXU/VA/phsA/val/mag", "VA"),
    arrayOf("MI/MMXU/VA/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXU/VA/phsB/val/mag", "VA"),
    arrayOf("MI/MMXU/VA/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXU/VA/phsC/val/mag", "VA"),
    arrayOf("MI/MMXU/VA/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXU/VA/neut/val/mag", "VA"),
    arrayOf("MI/MMXU/VA/neut/val/ang", "Deg"),
    arrayOf("MI/MMXU/PF/phsA/val/mag", "W/VA"),
    arrayOf("MI/MMXU/PF/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXU/PF/phsB/val/mag", "W/VA"),
    arrayOf("MI/MMXU/PF/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXU/PF/phsC/val/mag", "W/VA"),
    arrayOf("MI/MMXU/PF/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXU/PF/neut/val/mag", "W/VA"),
    arrayOf("MI/MMXU/PF/neut/val/ang", "Deg"),
    arrayOf("MI/MMXU/Z/phsA/val/mag", "Ohm"),
    arrayOf("MI/MMXU/Z/phsA/val/ang", "Deg"),
    arrayOf("MI/MMXU/Z/phsB/val/mag", "Ohm"),
    arrayOf("MI/MMXU/Z/phsB/val/ang", "Deg"),
    arrayOf("MI/MMXU/Z/phsC/val/mag", "Ohm"),
    arrayOf("MI/MMXU/Z/phsC/val/ang", "Deg"),
    arrayOf("MI/MMXU/Z/neut/val/mag", "Ohm"),
    arrayOf("MI/MMXU/Z/neut/val/ang", "Deg")
)

@Composable
fun HomeScreen() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxSize()) {
        SearchView(textState)
        TopicList(homeTopics, textState, focusManager)
    }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = {
            state.value = it
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        ),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
    )
}

@Composable
fun TopicList(topics: ArrayList<Array<String>>, state: MutableState<TextFieldValue>, focusManager: FocusManager) {
    var filteredTopics: ArrayList<Array<String>>
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        val searchedText = state.value.text.lowercase(Locale.getDefault())
        filteredTopics = if (searchedText.isEmpty()) {
            topics
        } else {
            val resultList = ArrayList<Array<String>>()
            for (topic in topics) {
                if (topic[0].lowercase(Locale.getDefault()).isMatch("*$searchedText*")) {
                    resultList.add(topic)
                }
            }
            resultList
        }
        items(filteredTopics) {
            TopicListItem(it[0], it[1], focusManager)
        }
    }
}

fun topicFloat(topic: String): Float? {
    var str = topics[topic] as String? ?: ""
    var m  =  1.0f
    if (str.endsWith("m")) {
        str = str.dropLast(1)
        m = 0.001f
    } else if (str.endsWith("k")) {
        str = str.dropLast(1)
        m = 1000.0f
    } else if (str.endsWith("M")) {
        str = str.dropLast(1)
        m = 1000000.0f
    } else if (str.endsWith("G")) {
        str = str.dropLast(1)
        m = 1000000000.0f
    } else if (str.endsWith("T")) {
        str = str.dropLast(1)
        m = 1000000000000.0f
    }
    val f = str.toFloatOrNull()
    if (str.isEmpty() || f  == null)
        return null
    else
        return f * m
}

@Composable
fun TopicListItem(
    topic: String,
    suffix: String,
    focusManager: FocusManager,
) {
    val context = LocalContext.current
    val topicString = topics[topic] as String? ?: ""
    val str = topicString.replace(Regex("[mkMGT]$"), "")
    val isError = (str.toFloatOrNull() == null || str.isEmpty()) &&
            (str.isNotEmpty() || str.length != topicString.length)
    var text by remember { mutableStateOf(topicString) }.apply { value = topicString }
    Row {
        TextField(
            modifier = Modifier.weight(3.0f).height(56.dp),
            value = text,
            onValueChange = {
                text = it
                topics[topic] = it
            },
            label = {
                Text(topic)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onDone = {},
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
            ),
            isError = isError,
            visualTransformation = if (text.isEmpty() || isError)
                VisualTransformation.None
            else
                SuffixTransformation(suffix)
        )
        Button(
            modifier = Modifier.weight(1.0f).height(55.dp),
            shape = RectangleShape,
            onClick = {
                if (mqttClient?.isConnected() == true) {
                    val f = topicFloat(topic)
                    if (f != null) {
                        mqttClient?.publish(topic, "$f", 1, false,
                            object : IMqttActionListener {
                                override fun onSuccess(asyncActionToken: IMqttToken?) {
                                    val msg = "Publish message: ${topics[topic]} to topic: $topic"
                                    Log.d(this.javaClass.name, msg)
                                    //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                }

                                override fun onFailure(
                                    asyncActionToken: IMqttToken?,
                                    exception: Throwable?
                                ) {
                                    Log.d(this.javaClass.name, "Failed to publish message to topic")
                                }
                            })
                    }
                } else {
                    Log.d(this.javaClass.name, "Impossible to publish, no server connected")
                }
            },
            enabled = !isError && text.isNotEmpty()
        ) {
            Text(stringResource(R.string.send))
        }
    }
}

class SuffixTransformation(private val suffix: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val result = text + AnnotatedString(suffix)
        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset
            }
            override fun transformedToOriginal(offset: Int): Int {
                if (text.isEmpty()) return 0
                if (offset >=  text.length) return text.length
                return offset
            }
        }
        return TransformedText(result, numberOffsetTranslator)
    }
}

class PrefixTransformation(private val prefix: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val result = AnnotatedString(prefix) + text
        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset + prefix.length
            }
            override fun transformedToOriginal(offset: Int): Int {
                if (offset < prefix.length) return 0
                return offset - prefix.length
            }
        }
        return TransformedText(result, numberOffsetTranslator)
    }
}

fun String.isMatch(pattern: String): Boolean {
    var sIdx = 0
    var pIdx = 0
    var lastWildcardIdx = -1
    var sBacktrackIdx = -1
    var nextToWildcardIdx = -1
    while (sIdx < length) {
        if (pIdx < pattern.length && (pattern[pIdx] == '?' || pattern[pIdx] == this[sIdx])) {
            ++sIdx
            ++pIdx
        } else if (pIdx < pattern.length && pattern[pIdx] == '*') {
            lastWildcardIdx = pIdx
            nextToWildcardIdx = ++pIdx
            sBacktrackIdx = sIdx
        } else if (lastWildcardIdx == -1) {
            return false
        } else {
            pIdx = nextToWildcardIdx
            sIdx = ++sBacktrackIdx
        }
    }
    for (i in pIdx until pattern.length) {
        if (pattern[i] != '*') {
            return false
        }
    }
    return true
}

@Preview
@Composable
fun HomeScreenPreview() {
    OmicronTheme {
        HomeScreen()
    }
}
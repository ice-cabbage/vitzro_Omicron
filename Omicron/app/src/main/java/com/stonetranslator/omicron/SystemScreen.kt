package com.stonetranslator.omicron

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stonetranslator.omicron.ui.theme.OmicronTheme
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import java.util.Locale

val systemTopics = arrayListOf(
    "System/SymStu/Str/all/bit1",
    "System/SymStu/Str/all/bit2",
    "System/SymStu/Str/all/bit3",
    "System/SymStu/Str/all/bit4",
    "System/SymStu/Str/all/bit5",
    "System/SymStu/Op/all/bit1",
    "System/SymStu/Op/all/bit2",
    "System/SymStu/Op/all/bit3",
    "System/SymStu/Op/all/bit4",
    "System/SymStu/Op/all/bit5",
    "System/SymStu/Remote",
    "System/SymStu/Local",
    "System/SymStu/DIStatus1/all",
    "System/SymStu/DIStatus2/all",
    "System/SymStu/DIStatus3/all",
    "System/SymStu/DIStatus4/all",
    "System/SymStu/DIStatus5/all",
    "System/SymStu/DIStatus6/all",
    "System/SymStu/DOStatus1/all",
    "System/SymStu/DOStatus2/all",
    "System/SymStu/DOStatus3/all",
    "System/SymStu/DOStatus4/all",
    "System/SymStu/LED1/all",
    "System/SymStu/LED2/all",
    "System/SymStu/Key1/all",
    "System/SymStu/CBStatus1",
    "System/SymStu/CBStatus2",
    "System/SymStu/A_phsA_Change_Point",
    "System/SymStu/A_phsB_Change_Point",
    "System/SymStu/A_phsC_Change_Point",
    "System/SymStu/A_neut_Change_Point",
    "System/SymStu/EmergencyStr",
    "System/SymStu/eSystemMode",
    "System/SymClt/DIClt1/all",
    "System/SymClt/DIClt2/all",
    "System/SymClt/DIClt3/all",
    "System/SymClt/DIClt4/all",
    "System/SymClt/DIClt5/all",
    "System/SymClt/DIClt6/all",
    "System/SymClt/DOClt1/all",
    "System/SymClt/DOClt2/all",
    "System/SymClt/DOClt3/all",
    "System/SymClt/DOClt4/all",
    "System/SymClt/LED1/all",
    "System/SymClt/LED2/all",
    "System/SymClt/Key1/all",
    "System/EvCkFg",
    "System/SymComStu",
    "System/SymDoCnt",
    "System/Simul",
)

@Composable
fun SystemScreen() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxSize()) {
        SearchView(textState)
        SystemTopicList(state = textState, focusManager)
    }
}

@Composable
fun SystemTopicList(state: MutableState<TextFieldValue>, focusManager: FocusManager) {
    var filteredTopics: ArrayList<String>
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        val searchedText = state.value.text.lowercase(Locale.getDefault())
        filteredTopics = if (searchedText.isEmpty()) {
            systemTopics
        } else {
            val resultList = ArrayList<String>()
            for (topic in systemTopics) {
                if (topic.lowercase(Locale.getDefault()).isMatch("*$searchedText*")) {
                    resultList.add(topic)
                }
            }
            resultList
        }
        items(filteredTopics) {
            SystemTopicListItem(it, focusManager)
        }
    }
}

@Composable
fun SystemTopicListItem(
    topic: String,
    focusManager: FocusManager
) {
    val context = LocalContext.current
    val topicString = topics[topic] as String? ?: ""
    val isError = topicString.toUIntOrNull(16) == null && topicString.isNotEmpty()
    var text by remember { mutableStateOf(topicString) }.apply {value = topicString}
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
                PrefixTransformation("0x")
        )
        Button(
            modifier = Modifier.weight(1.0f).height(55.dp),
            shape = RectangleShape,
            onClick = {
                if (mqttClient?.isConnected() == true) {
                    val h = topicString.toUIntOrNull(16)
                    if (h != null) {
                        mqttClient?.publish(topic, "0x${h.toString(16)}", 1, false,
                            object : IMqttActionListener {
                                override fun onSuccess(asyncActionToken: IMqttToken?) {
                                    val msg = "Publish message: ${topics[topic]} to topic: $topic"
                                    Log.d(this.javaClass.name, msg)
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
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

@Preview
@Composable
fun SystemScreenPreview() {
    OmicronTheme() {
        SystemScreen()
    }
}
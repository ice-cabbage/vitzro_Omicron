package com.stonetranslator.omicron

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stonetranslator.omicron.ui.theme.OmicronTheme
import java.util.Timer
import java.util.TimerTask
import kotlin.math.absoluteValue
import kotlin.math.sin

data class InputValueState(
    var va: Float = 110.0f,
    var vaAngle: Float = 0.0f,
    var vb: Float = 110.0f,
    var vbAngle: Float = 240.0f,
    var vc: Float = 110.0f,
    var vcAngle: Float = 120.0f,
    var ia: Float = 5.0f,
    var iaAngle: Float = 0.0f,
    var ib: Float = 5.0f,
    var ibAngle: Float = 240.0f,
    var ic: Float = 5.0f,
    var icAngle: Float = 120.0f,
    var frequency: Float = 60.0f,
    var running: Boolean = false,
)

sealed class InputValueEvent {
    data class RunningChanged(val value: Boolean): InputValueEvent()
    data class Apply(val value: InputValueState): InputValueEvent()
}

@Composable
fun SiInput(label: Int, text: String, onValueChange: (String)->Unit, suffix: String, modifier: Modifier) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(stringResource(label)) },
        isError = text.toSiFloatOrNull() == null,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = if (text.isEmpty() || text.toSiFloatOrNull() == null)
            VisualTransformation.None
        else
            SuffixTransformation(suffix)
    )
}

@Composable
fun InputValueScreen(viewModel: InputValueViewModel = viewModel()) {
    val state by viewModel.svState.collectAsState()
    var vaM by remember { mutableStateOf(state.va.toSiString("%.02f")) }
    var vbM by remember { mutableStateOf(state.vb.toSiString("%.02f")) }
    var vcM by remember { mutableStateOf(state.vc.toSiString("%.02f")) }
    var vaA by remember { mutableStateOf(state.vaAngle.toSiString("%.02f")) }
    var vbA by remember { mutableStateOf(state.vbAngle.toSiString("%.02f")) }
    var vcA by remember { mutableStateOf(state.vcAngle.toSiString("%.02f")) }
    var iaM by remember { mutableStateOf(state.ia.toSiString("%.02f")) }
    var ibM by remember { mutableStateOf(state.ib.toSiString("%.02f")) }
    var icM by remember { mutableStateOf(state.ic.toSiString("%.02f")) }
    var iaA by remember { mutableStateOf(state.iaAngle.toSiString("%.02f")) }
    var ibA by remember { mutableStateOf(state.ibAngle.toSiString("%.02f")) }
    var icA by remember { mutableStateOf(state.icAngle.toSiString("%.02f")) }
    var frequency by remember { mutableStateOf(state.frequency.toSiString("%.02f")) }
    val orientation = LocalConfiguration.current.orientation
    LazyColumn {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            item {
                Row {
                    SiInput(R.string.va, vaM, { vaM = it }, "V", Modifier.weight(1.0f))
                    SiInput(R.string.vb, vbM, { vbM = it }, "V", Modifier.weight(1.0f))
                    SiInput(R.string.vc, vcM, { vcM = it }, "V", Modifier.weight(1.0f))
                }
            }
            item {
                Row {
                    SiInput(R.string.va_angle, vaA, { vaA = it }, "\u00b0", Modifier.weight(1.0f))
                    SiInput(R.string.vb_angle, vbA, { vbA = it }, "\u00b0", Modifier.weight(1.0f))
                    SiInput(R.string.vc_angle, vcA, { vcA = it }, "\u00b0", Modifier.weight(1.0f))
                }
            }
            item {
                Row {
                    SiInput(R.string.ia, iaM, { iaM = it }, "A", Modifier.weight(1.0f))
                    SiInput(R.string.ib, ibM, { ibM = it }, "A", Modifier.weight(1.0f))
                    SiInput(R.string.ic, icM, { icM = it }, "A", Modifier.weight(1.0f))
                }
            }
            item {
                Row {
                    SiInput(R.string.ia_angle, iaA, { iaA = it }, "\u00b0", Modifier.weight(1.0f))
                    SiInput(R.string.ib_angle, ibA, { ibA = it }, "\u00b0", Modifier.weight(1.0f))
                    SiInput(R.string.ic_angle, icA, { icA = it }, "\u00b0", Modifier.weight(1.0f))
                }
            }
        } else {
            item {
                Row {
                    SiInput(R.string.va, vaM, { vaM = it }, "V", Modifier.weight(1.0f))
                    SiInput(R.string.va_angle, vaA, { vaA = it }, "\u00b0", Modifier.weight(1.0f))
                    SiInput(R.string.vb, vbM, { vbM = it }, "V", Modifier.weight(1.0f))
                    SiInput(R.string.vb_angle, vbA, { vbA = it }, "\u00b0", Modifier.weight(1.0f))
                    SiInput(R.string.vc, vcM, { vcM = it }, "V", Modifier.weight(1.0f))
                    SiInput(R.string.vc_angle, vcA, { vcA = it }, "\u00b0", Modifier.weight(1.0f))
                }
            }
            item {
                Row {
                    SiInput(R.string.ia, iaM, { iaM = it }, "A", Modifier.weight(1.0f))
                    SiInput(R.string.ia_angle, iaA, { iaA = it }, "\u00b0", Modifier.weight(1.0f))
                    SiInput(R.string.ib, ibM, { ibM = it }, "A", Modifier.weight(1.0f))
                    SiInput(R.string.ib_angle, ibA, { ibA = it }, "\u00b0", Modifier.weight(1.0f))
                    SiInput(R.string.ic, icM, { icM = it }, "A", Modifier.weight(1.0f))
                    SiInput(R.string.ic_angle, icA, { icA = it }, "\u00b0", Modifier.weight(1.0f))
                }
            }
        }
        item {
            SiInput(R.string.frequency, frequency, {frequency = it}, "Hz", Modifier.fillMaxWidth())
        }
        item {
            Row() {
                Button(
                    onClick = {
                        val InputValueState = InputValueState()
                        InputValueState.va = vaM.toSiFloatOrNull() ?: state.va
                        InputValueState.vb = vbM.toSiFloatOrNull() ?: state.vb
                        InputValueState.vc = vcM.toSiFloatOrNull() ?: state.vc
                        InputValueState.ia = iaM.toSiFloatOrNull() ?: state.ia
                        InputValueState.ib = ibM.toSiFloatOrNull() ?: state.ib
                        InputValueState.ic = icM.toSiFloatOrNull() ?: state.ic
                        InputValueState.vaAngle = vaA.toSiFloatOrNull() ?: state.vaAngle
                        InputValueState.vbAngle = vbA.toSiFloatOrNull() ?: state.vbAngle
                        InputValueState.vcAngle = vcA.toSiFloatOrNull() ?: state.vcAngle
                        InputValueState.iaAngle = iaA.toSiFloatOrNull() ?: state.iaAngle
                        InputValueState.ibAngle = ibA.toSiFloatOrNull() ?: state.ibAngle
                        InputValueState.icAngle = icA.toSiFloatOrNull() ?: state.icAngle
                        InputValueState.frequency = frequency.toSiFloatOrNull() ?: state.frequency
                        InputValueState.running = state.running
                        viewModel.onEvent(InputValueEvent.Apply(InputValueState))
                    },
                    modifier = Modifier.weight(1.0f),
                    shape = RectangleShape,
                    enabled = !vaM.isEqualOrNull(state.va) || !vbM.isEqualOrNull(state.vb) || !vcM.isEqualOrNull(state.vc) ||
                            !iaM.isEqualOrNull(state.ia) || !ibM.isEqualOrNull(state.ib) || !icM.isEqualOrNull(state.ic) ||
                            !vaA.isEqualOrNull(state.vaAngle) || !vbA.isEqualOrNull(state.vbAngle) || !vcA.isEqualOrNull(state.vcAngle) ||
                            !iaA.isEqualOrNull(state.iaAngle) || !ibA.isEqualOrNull(state.ibAngle) || !icA.isEqualOrNull(state.icAngle) ||
                            !frequency.isEqualOrNull(state.frequency)
                ) {
                    Text(stringResource(R.string.apply))
                }
                Button(
                    onClick = {
                        if (!state.running) {
                            viewModel.timer = Timer()
                            viewModel.timer?.scheduleAtFixedRate(object : TimerTask() {
                                override fun run() {
                                    val a = arrayOf(
                                        state.va * 1.414f, state.vb * 1.414f, state.vc * 1.414f,
                                        state.ia * 1.414f, state.ib * 1.414f, state.ic * 1.414f
                                    )
                                    val b = 2 * Math.PI.toFloat() * state.frequency / 3600
                                    val c = arrayOf(
                                        state.vaAngle * 2 * Math.PI.toFloat() / 360,
                                        state.vbAngle * 2 * Math.PI.toFloat() / 360,
                                        state.vcAngle * 2 * Math.PI.toFloat() / 360,
                                        state.iaAngle * 2 * Math.PI.toFloat() / 360,
                                        state.ibAngle * 2 * Math.PI.toFloat() / 360,
                                        state.icAngle * 2 * Math.PI.toFloat() / 360
                                    )
                                    val s = arrayOf(100, 100, 100, 1000, 1000, 1000)
                                    val sv = IntArray(144 * 6) { i ->
                                        (a[i % 6] * sin(b * (i / 6 + viewModel.svOffset) + c[i % 6]) * s[i % 6]).toInt()
                                    }
                                    viewModel.svOffset += 144
                                    viewModel.svOffset %= 3600
                                    mqttClient?.publish("Input_value", sv)
                                }
                            }, 40, 40)
                        } else {
                            viewModel.timer?.cancel()
                            viewModel.timer?.purge()
                            viewModel.timer = null
                        }
                        viewModel.onEvent(InputValueEvent.RunningChanged(!state.running))
                    },
                    modifier = Modifier.weight(1.0f),
                    shape = RectangleShape
                ) {
                    Text(stringResource(if (state.running) R.string.stop else R.string.run))
                }
            }
        }
    }
}

fun Float.toSiString(format: String): String {
    return if (this.absoluteValue < 1000) format.format(this)
    else if (this.absoluteValue < 1000000.0f) "${format.format(this / 1000.0f)}k"
    else if (this.absoluteValue < 1000000000.0f) "${format.format(this / 1000000.0f)}M"
    else if (this.absoluteValue < 1000000000000.0f) "${format.format(this / 1000000000.0f)}G"
    else "${format.format(this / 1000000000000.0f)}T"
}

fun String.toSiFloat(): Float {
    return this.toSiFloatOrNull() ?: 0.0f
}

fun String.toSiFloatOrNull(): Float? {
    var str = this
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
    return if (str.isEmpty() || f  == null) null else f * m
}

fun String.isEqualOrNull(value: Float): Boolean {
    val v = this.toSiFloatOrNull()
    return v == null || v == value
}

@Preview
@Composable
fun WaveScreenPreview() {
    OmicronTheme {
        InputValueScreen()
    }
}
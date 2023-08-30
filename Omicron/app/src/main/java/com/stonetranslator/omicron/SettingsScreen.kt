package com.stonetranslator.omicron

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.stonetranslator.omicron.ui.theme.OmicronTheme
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        var serverUri by rememberSaveable { mutableStateOf("tcp://10.0.2.2:1883") }
        var clientId by rememberSaveable { mutableStateOf("") }
        var username by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var pubTopic by rememberSaveable { mutableStateOf("") }
        var pubMessage by rememberSaveable { mutableStateOf("") }
        var subTopic by rememberSaveable { mutableStateOf("") }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        val context = LocalContext.current
        var connected by rememberSaveable { mutableStateOf(mqttClient?.isConnected() ?: false) }
        val keyboardController = LocalSoftwareKeyboardController.current

        if (!connected) {
            TextField(
                value = serverUri,
                onValueChange = {value ->
                    serverUri = value
                },
                label = { Text(text = stringResource(id = R.string.server_uri)) },
                placeholder = { Text(text = stringResource(id = R.string.type_server_uri)) },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = clientId,
                onValueChange = {value ->
                    clientId = value
                },
                label = { Text(text = stringResource(id = R.string.client_id)) },
                placeholder = { Text(text = stringResource(id = R.string.type_client_id)) },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = username,
                onValueChange = {value ->
                    username = value
                },
                label = { Text(text = stringResource(id = R.string.username)) },
                placeholder = { Text(text = stringResource(id = R.string.type_username)) },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = password,
                onValueChange = {value ->
                    password = value
                },
                label = { Text(text = stringResource(id = R.string.password)) },
                placeholder = { Text(text = stringResource(id = R.string.type_password)) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        if (passwordVisible)
                            Icon(painterResource(R.drawable.ic_visibility), "Visibility")
                        else
                            Icon(
                                painterResource(R.drawable.ic_visibility_off),
                                "Visibility off"
                            )
                    }
                }
            )
            ElevatedButton(
                onClick = {
                    Log.d("", "$serverUri, $clientId, $username, $password")
                    mqttClient = MQTTClient(context, serverUri, clientId)
                    mqttClient?.connect(username, password,
                        object : IMqttActionListener {
                            override fun onSuccess(asyncActionToken: IMqttToken?) {
                                Log.d(this.javaClass.name, "Connection success")
                                connected = true
                            }

                            override fun onFailure(
                                asyncActionToken: IMqttToken?,
                                exception: Throwable?
                            ) {
                                Log.d(
                                    this.javaClass.name,
                                    "Connection failure: ${exception.toString()}"
                                )
                            }
                        },
                        object : MqttCallback {
                            override fun messageArrived(topic: String?, message: MqttMessage?) {
                                mqttClient?.defaultCbClient?.messageArrived(topic, message)
                                val msg =
                                    "Receive message: ${message.toString()} from topic: $topic"
                                Log.d(this.javaClass.name, msg)
                            }

                            override fun connectionLost(cause: Throwable?) {
                                mqttClient?.defaultCbClient?.connectionLost(cause)
                                Log.d(
                                    this.javaClass.name,
                                    "Connection lost ${cause.toString()}"
                                )
                                connected = false
                            }
                            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                                mqttClient?.defaultCbClient?.deliveryComplete(token)
                                Log.d(this.javaClass.name, "Delivery complete")
                            }
                        }
                    )
                    keyboardController?.hide()
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.connect))
            }
        } else {
            TextField(
                value = pubTopic,
                onValueChange = {value ->
                    pubTopic = value
                },
                label = { Text(text = stringResource(id = R.string.topic)) },
                placeholder = { Text(text = stringResource(id = R.string.type_topic)) },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = pubMessage,
                onValueChange = {value ->
                    pubMessage = value
                },
                label = { Text(text = stringResource(id = R.string.message)) },
                placeholder = { Text(text = stringResource(id = R.string.type_message)) },
                modifier = Modifier.fillMaxWidth()
            )
            ElevatedButton(
                onClick = {
                    if (mqttClient?.isConnected() == true) {
                        mqttClient?.publish(pubTopic, pubMessage, 1, false,
                            object : IMqttActionListener {
                                override fun onSuccess(asyncActionToken: IMqttToken?) {
                                    val msg ="Publish message: $pubMessage to topic: $pubTopic"
                                    Log.d(this.javaClass.name, msg)
                                }

                                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                                    Log.d(this.javaClass.name, "Failed to publish message to topic")
                                }
                            })
                    } else {
                        Log.d(this.javaClass.name, "Impossible to publish, no server connected")
                        connected = false
                    }
                    keyboardController?.hide()
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.publish))
            }
            TextField(
                value = subTopic,
                onValueChange = {value ->
                    subTopic = value
                },
                label = { Text(text = stringResource(id = R.string.topic)) },
                placeholder = { Text(text = stringResource(id = R.string.type_topic)) },
                modifier = Modifier.fillMaxWidth()
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                ElevatedButton(
                    onClick = {
                        if (mqttClient?.isConnected() == true) {
                            mqttClient?.subscribe(subTopic, 1,
                                object : IMqttActionListener {
                                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                                        val msg = "Subscribed to: $subTopic"
                                        Log.d(this.javaClass.name, msg)
                                    }

                                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                                        Log.d(this.javaClass.name, "Failed to subscribe: $subTopic")
                                    }
                                })
                        } else {
                            Log.d(this.javaClass.name, "Impossible to subscribe, no server connected")
                            connected = false
                        }
                        keyboardController?.hide()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.subscribe))
                }
                ElevatedButton(
                    onClick = {
                        if (mqttClient?.isConnected() == true) {
                            mqttClient?.unsubscribe( subTopic,
                                object : IMqttActionListener {
                                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                                        val msg = "Unsubscribed to: $subTopic"
                                        Log.d(this.javaClass.name, msg)
                                    }

                                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                                        Log.d(this.javaClass.name, "Failed to unsubscribe: $subTopic")
                                    }
                                })
                        } else {
                            Log.d(this.javaClass.name, "Impossible to unsubscribe, no server connected")
                            connected = false
                        }
                        keyboardController?.hide()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.unsubscribe))
                }
            }
            ElevatedButton(
                onClick = {
                    if (mqttClient?.isConnected() == true) {
                        // Disconnect from MQTT Broker
                        mqttClient?.disconnect(object : IMqttActionListener {
                            override fun onSuccess(asyncActionToken: IMqttToken?) {
                                Log.d(this.javaClass.name, "Disconnected")
                                connected = false
                            }

                            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                                Log.d(this.javaClass.name, "Failed to disconnect")
                            }
                        })
                    } else {
                        Log.d(this.javaClass.name, "Impossible to disconnect, no server connected")
                        connected = false;
                    }
                    keyboardController?.hide()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.disconnect))
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    OmicronTheme {
        HomeScreen()
    }
}
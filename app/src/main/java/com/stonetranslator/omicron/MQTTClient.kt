package com.stonetranslator.omicron

import android.content.Context
import android.util.Log
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.nio.ByteBuffer

var mqttClient: MQTTClient? = null

class MQTTClient(context: Context, serverURI: String, clientID: String = "") {
    private var mqttClient = MqttAndroidClient(context, serverURI, clientID)
    private val defaultCbConnect = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.d(this.javaClass.name, "(Default) Connection success")
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.d(this.javaClass.name, "Connection failure: ${exception.toString()}")
        }
    }

    val defaultCbClient = object : MqttCallback {
        override fun messageArrived(topic: String?, message: MqttMessage?) {
            if (topic != null) {
                if (message != null) {
                    val str = message.toString()
                    if (str.matches(Regex("^[\\p{Nd}]+$")))  {
                        topics.put(topic, str.toInt())
                    } else if (str.matches(Regex("^[\\p{Nd}]+[.][\\p{Nd}Ee]+$"))) {
                        topics.put(topic, str.toFloat())
                    } else {
                        topics.put(topic, str)
                    }
                }
            }
        }

        override fun connectionLost(cause: Throwable?) {
            Log.d(this.javaClass.name, "Connection lost ${cause.toString()}")
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            Log.d(this.javaClass.name, "Delivery completed")
        }
    }
    private val defaultCbSubscribe = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.d(this.javaClass.name, "Subscribed to topic")
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.d(this.javaClass.name, "Failed to subscribe topic")
        }
    }
    private val defaultCbUnsubscribe = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.d(this.javaClass.name, "Unsubscribed to topic")
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.d(this.javaClass.name, "Failed to unsubscribe topic")
        }
    }
    private val defaultCbPublish = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.d(this.javaClass.name, "Message published to topic")
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.d(this.javaClass.name, "Failed to publish message to topic")
        }
    }
    private val defaultCbDisconnect = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.d(this.javaClass.name, "Disconnected")
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.d(this.javaClass.name, "Failed to disconnect")
        }
    }

    fun connect(username:   String               = "",
                password:   String               = "",
                cbConnect: IMqttActionListener = defaultCbConnect,
                cbClient: MqttCallback = defaultCbClient) {
        mqttClient.setCallback(cbClient)
        val options = MqttConnectOptions()
        options.userName = username
        options.password = password.toCharArray()

        try {
            mqttClient.connect(options, null, cbConnect)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun isConnected(): Boolean {
        return mqttClient.isConnected
    }

    fun subscribe(topic:        String,
                  qos:          Int                 = 1,
                  cbSubscribe: IMqttActionListener = defaultCbSubscribe) {
        try {
            mqttClient.subscribe(topic, qos, null, cbSubscribe)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun unsubscribe(topic:          String,
                    cbUnsubscribe: IMqttActionListener = defaultCbUnsubscribe) {
        try {
            mqttClient.unsubscribe(topic, null, cbUnsubscribe)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(topic:      String,
                msg:        String,
                qos:        Int                 = 1,
                retained:   Boolean             = false,
                cbPublish: IMqttActionListener = defaultCbPublish) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, cbPublish)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(topic:      String,
                msg:        IntArray,
                qos:        Int                 = 1,
                retained:   Boolean             = false,
                cbPublish: IMqttActionListener = defaultCbPublish) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, cbPublish)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun disconnect(cbDisconnect: IMqttActionListener = defaultCbDisconnect ) {
        try {
            mqttClient.disconnect(null, cbDisconnect)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}

fun IntArray.toByteArray(): ByteArray {
    val bytes = ByteBuffer.allocate(this.size * Int.SIZE_BYTES)
    bytes.asIntBuffer().put(this)
    return bytes.array()
}

fun  ByteArray.toIntArray(): IntArray {
    val ints = IntArray(this.size / Int.SIZE_BYTES)
    ByteBuffer.wrap(this).asIntBuffer()[ints]
    return ints
}
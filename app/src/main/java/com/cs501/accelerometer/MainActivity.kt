/**
 * References:
 * 1.https://www.sciencebuddies.org/google-science-journal-app-tutorial-part5-accelerometer
 * 2.https://developer.android.com/reference/kotlin/android/hardware/SensorEventListener
 * 3.https://stackoverflow.com/questions/4128660/android-sensoreventlistener-syntax
 * 4.https://developer.android.com/reference/kotlin/android/hardware/SensorManager
 */

package com.cs501.accelerometer

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var threshold: Int = 30
    private val maxWarningThreshold: Int = 50
    private lateinit var thresholdValueTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val seekBar = findViewById<SeekBar>(R.id.thresholdSeekBar)
        thresholdValueTextView = findViewById(R.id.thresholdValueTextView)

        // Update TextView
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                threshold = progress
                thresholdValueTextView.text = "Current Threshold: $threshold"
                if (threshold > maxWarningThreshold) {
                    Toast.makeText(this@MainActivity, "Warning: High threshold set!",
                        Toast.LENGTH_SHORT).show()
                }
                if (threshold < 5) {
                    Toast.makeText(this@MainActivity, "Warning: Low threshold set!",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val xValue = event.values[0]
            val yValue = event.values[1]
            val zValue = event.values[2]
            if (Math.abs(xValue) > threshold) {
                Toast.makeText(this, "Significant movement detected on X-axis!", Toast.LENGTH_SHORT).show()
            }
            if (Math.abs(yValue) > threshold) {
                Toast.makeText(this, "Significant movement detected on Y-axis!", Toast.LENGTH_SHORT).show()
            }
            if (Math.abs(zValue) > threshold) {
                Toast.makeText(this, "Significant movement detected on Z-axis!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
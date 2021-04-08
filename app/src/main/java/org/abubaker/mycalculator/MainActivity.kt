package org.abubaker.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.abubaker.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Helper variables for . (decimal point)
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    // Binding Object
    private lateinit var binding: ActivityMainBinding

    // onCreate()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // It will load activity_main.xml view
        // setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }

    // onDigit() -  on clicking 0-9 Digits
    fun onDigit(view: View) {
        binding.tvInput.append((view as Button).text)
        lastNumeric = true
    }

    // onClear() - CLR
    fun onClear(view: View) {
        binding.tvInput.text = ""

        // reset helper variables
        lastNumeric = false
        lastDot = false

    }

    // onDecimalPoint()
    fun onDecimalPoint(view: View) {

        // Only runs if
        if (lastNumeric && !lastDot) {
            binding.tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }

    }

    // onEqual() =
    fun onEqual(view: View) {

        // Operation should not be performed on the null input
        if (lastNumeric) {
            val tvValue = binding.tvInput.text.toString()

            try {
                // - Split text to prepare
                val splitValue = tvValue.split("-")

                // Store Filtered values from the "split
                var one = splitValue[0] // Left Side
                var two = splitValue[1] // Right Side

                // - Subtract values and update Display (tvInput) with the result
                if (tvValue.contains("-")) {
                    binding.tvInput.text = (one.toDouble() - two.toDouble()).toString()
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }

        }

    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(binding.tvInput.text.toString())) {
            binding.tvInput.append((view as Button).text)

            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean {

        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("-")
        }

    }


}
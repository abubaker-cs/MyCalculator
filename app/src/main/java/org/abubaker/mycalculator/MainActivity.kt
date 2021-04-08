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

    fun onClear(view: View) {
        binding.tvInput.setText("")

        // reset helper variables
        lastNumeric = false
        lastDot = false

    }

    fun onDecimalPoint(view: View) {

        // Only runs if
        if (lastNumeric && !lastDot) {
            binding.tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }

    }

}
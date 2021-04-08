package org.abubaker.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
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

        reset()

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
            var tvValue = binding.tvInput.text.toString()
            var prefix = ""

            try {
                /**
                 * Safety Check: -89-40
                 * In the above example, splitter will retrieve 3 values, i.e. -, 89 and 40
                 * To overcome this issue, we will first check if the provide value starts with -
                 * If true, then we will start splitting string from 1 index value
                 */
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                /**
                 * - Subtract values and update Display (tvInput) with the result
                 */
                if (tvValue.contains("-")) {

                    // performCalculation("-")

                    // - Split text to prepare
                    val splitValue = tvValue.split("-")

                    // Store Filtered values from the "split
                    var one = splitValue[0] // Left Side
                    var two = splitValue[1] // Right Side

                    // If prefix is not empty then use the original provided value
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput.text =
                        removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                } else if (tvValue.contains("+")) {
                    // - Split text to prepare
                    val splitValue = tvValue.split("+")

                    // Store Filtered values from the "split
                    var one = splitValue[0] // Left Side
                    var two = splitValue[1] // Right Side

                    // If prefix is not empty then use the original provided value
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput.text =
                        removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                } else if (tvValue.contains("/")) {
                    // - Split text to prepare
                    val splitValue = tvValue.split("/")

                    // Store Filtered values from the "split
                    var one = splitValue[0] // Left Side
                    var two = splitValue[1] // Right Side

                    // If prefix is not empty then use the original provided value
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput.text =
                        removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                } else if (tvValue.contains("*")) {
                    // - Split text to prepare
                    val splitValue = tvValue.split("*")

                    // Store Filtered values from the "split
                    var one = splitValue[0] // Left Side
                    var two = splitValue[1] // Right Side

                    // If prefix is not empty then use the original provided value
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput.text =
                        removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())

                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }

        }

    }

    private fun performCalculation(s: String) {
        Toast.makeText(this, "You sent me: $s", Toast.LENGTH_SHORT).show()
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result

        // Use the string as it is, but get rid of last two characters from end of the index, i.e. 99.0 = 99
        /**
         * If the input is 33.01 + 33.01, then the outcome should be 66.02 instead of 66.
         * The removeZeroAfterDot should use "endsWith" instead of "contains".
         * Ref: https://www.udemy.com/course/android-kotlin-developer/learn/lecture/17998853#questions/11263624
         */
        if (result.endsWith(".0"))
            value = result.substring(0, result.length - 2)

        return value
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(binding.tvInput.text.toString())) {
            binding.tvInput.append((view as Button).text)

            reset()
        }
    }

    private fun isOperatorAdded(value: String): Boolean {

        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("-")
        }

    }

    // reset helper variables
    private fun reset() {
        lastNumeric = false
        lastDot = false
    }


}
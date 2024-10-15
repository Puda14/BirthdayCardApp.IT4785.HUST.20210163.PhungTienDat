package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var textResult: EditText

    private var op: Int = 0
    private var op1: Double = 0.0
    private var op2: Double = 0.0
    private var currentInput: String = ""
    private var isOp1: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.editTextText)

        // Set onClick listeners for all buttons
        findViewById<Button>(R.id.btn0).setOnClickListener(this)
        findViewById<Button>(R.id.btn1).setOnClickListener(this)
        findViewById<Button>(R.id.btn2).setOnClickListener(this)
        findViewById<Button>(R.id.btn3).setOnClickListener(this)
        findViewById<Button>(R.id.btn4).setOnClickListener(this)
        findViewById<Button>(R.id.btn5).setOnClickListener(this)
        findViewById<Button>(R.id.btn6).setOnClickListener(this)
        findViewById<Button>(R.id.btn7).setOnClickListener(this)
        findViewById<Button>(R.id.btn8).setOnClickListener(this)
        findViewById<Button>(R.id.btn9).setOnClickListener(this)
        findViewById<Button>(R.id.btnAdd).setOnClickListener(this)
        findViewById<Button>(R.id.btnSubtract).setOnClickListener(this)
        findViewById<Button>(R.id.btnMultiply).setOnClickListener(this)
        findViewById<Button>(R.id.btnDivide).setOnClickListener(this)
        findViewById<Button>(R.id.btnEqual).setOnClickListener(this)
        findViewById<Button>(R.id.btnClearEntry).setOnClickListener(this)
        findViewById<Button>(R.id.btnClear).setOnClickListener(this)
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener(this)
        findViewById<Button>(R.id.btnDot).setOnClickListener(this)
        findViewById<Button>(R.id.btnBackspace).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val id = view?.id
        when (id) {
            R.id.btn0 -> addDigit(0)
            R.id.btn1 -> addDigit(1)
            R.id.btn2 -> addDigit(2)
            R.id.btn3 -> addDigit(3)
            R.id.btn4 -> addDigit(4)
            R.id.btn5 -> addDigit(5)
            R.id.btn6 -> addDigit(6)
            R.id.btn7 -> addDigit(7)
            R.id.btn8 -> addDigit(8)
            R.id.btn9 -> addDigit(9)
            R.id.btnAdd -> setOperation(1)
            R.id.btnSubtract -> setOperation(2)
            R.id.btnMultiply -> setOperation(3)
            R.id.btnDivide -> setOperation(4)
            R.id.btnEqual -> calculate()
            R.id.btnClearEntry -> clearEntry()
            R.id.btnClear -> clearAll()
            R.id.btnPlusMinus -> toggleSign()
            R.id.btnDot -> addDecimalPoint()
            R.id.btnBackspace -> backspace()
        }
    }

    private fun addDigit(digit: Int) {
        currentInput += digit.toString()
        updateDisplay(currentInput)
    }

    private fun addDecimalPoint() {
        if (!currentInput.contains(".")) {
            currentInput += "."
            updateDisplay(currentInput)
        }
    }

    private fun setOperation(operation: Int) {
        if (currentInput.isNotEmpty()) {
            if (isOp1) {
                op1 = currentInput.toDouble()
                currentInput = ""
                isOp1 = false
            } else {
                calculateIntermediate()
            }
        }
        op = operation
    }

    private fun calculateIntermediate() {
        if (currentInput.isNotEmpty()) {
            op2 = currentInput.toDouble()
            var result = 0.0
            when (op) {
                1 -> result = op1 + op2
                2 -> result = op1 - op2
                3 -> result = op1 * op2
                4 -> if (op2 != 0.0) result = op1 / op2
            }
            updateDisplay(formatNumber(result))
            op1 = result
            currentInput = ""
        }
    }

    private fun calculate() {
        if (currentInput.isNotEmpty()) {
            op2 = currentInput.toDouble()
            var result = 0.0
            when (op) {
                1 -> result = op1 + op2
                2 -> result = op1 - op2
                3 -> result = op1 * op2
                4 -> if (op2 != 0.0) result = op1 / op2
            }
            updateDisplay(formatNumber(result))
            resetAfterCalculation(result)
        }
    }

    private fun resetAfterCalculation(result: Double) {
        op1 = result
        op2 = 0.0
        currentInput = ""
        isOp1 = true
        op = 0
    }

    private fun clearEntry() {
        currentInput = ""
        textResult.setText("0")
    }

    private fun clearAll() {
        currentInput = ""
        op1 = 0.0
        op2 = 0.0
        op = 0
        textResult.setText("0")
        isOp1 = true
    }

    private fun toggleSign() {
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toDouble()
            currentInput = formatNumber(-value)
            updateDisplay(currentInput)
        }
    }

    private fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            if (currentInput.isEmpty()) {
                textResult.setText("0")
            } else {
                updateDisplay(currentInput)
            }
        }
    }

    private fun formatNumber(value: Double): String {
        return if (value == value.toInt().toDouble()) {
            value.toInt().toString()
        } else {
            value.toString()
        }
    }

    private fun updateDisplay(value: String) {
        textResult.setText(value)
    }
}

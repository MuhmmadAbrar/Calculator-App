package com.example.abrarcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException
import java.sql.Types.NULL

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null

    var lastNumeric : Boolean = false
    var lastDot : Boolean = false
    var lastOperator: Boolean=false
    var lastEqual: Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric=true
        lastOperator=false
    }

    fun onClear(view: View){
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append((view as Button).text)
            lastNumeric=false
            lastDot=true
        }
    }

    fun onOperator(view: View){
        if(lastNumeric && !lastOperator){
            tvInput?.append((view as Button).text)
            lastNumeric=false
            lastOperator=true
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try{
                if(tvValue.startsWith('-')){
                    prefix="-"
                    tvValue.substring(1)
                }
                val numbers = tvValue.split("[+\\-*/]".toRegex()).map { it.trim().toInt() }
                val operators = tvValue.split("[0-9]+".toRegex()).filter { it.isNotBlank() }

                var result = numbers[0]

                for (i in 1 until numbers.size) {
                    when (operators[i - 1]) {
                        "+" -> result += numbers[i]
                        "-" -> result -= numbers[i]
                        "*" -> result *= numbers[i]
                        "/" -> result /= numbers[i]
                    }
                }
                
                tvInput?.text = result.toString()


            }catch(e:ArithmeticException){
                e.printStackTrace()
                tvInput?.text= "Arithmetic Exception"
            }
        }
    }

}
package com.flytbase.assignment.supporting
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CalculatorHelper @Inject constructor() {

    fun multiAddDivSub(input: String?): Float {

        val inputString = ArrayList<String>()
        val mInputList = input!!.split(" ")

        for(i in mInputList) {
            inputString.add(i)
        }

        while (inputString.indexOf("*") != -1) {
            val firstNumber = inputString[inputString.indexOf("*") + 1].toDouble()
            val secondNumber = inputString[inputString.indexOf("*") - 1].toDouble()
            val answer = secondNumber * firstNumber
            inputString[inputString.indexOf("*") - 1] = java.lang.Double.toString(answer)
            inputString.removeAt(inputString.indexOf("*") + 1)
            inputString.removeAt(inputString.indexOf("*"))
        }

        while (inputString.indexOf("+") != -1) {
            val firstNumber = inputString[inputString.indexOf("+") + 1].toDouble()
            val secondNumber = inputString[inputString.indexOf("+") - 1].toDouble()
            val answer = secondNumber + firstNumber
            inputString[inputString.indexOf("+") - 1] = java.lang.Double.toString(answer)
            inputString.removeAt(inputString.indexOf("+") + 1)
            inputString.removeAt(inputString.indexOf("+"))
        }

        while (inputString.indexOf("/") != -1) {
            val firstNumber = inputString[inputString.indexOf("/") + 1].toDouble()
            val secondNumber = inputString[inputString.indexOf("/") - 1].toDouble()
            val answer = secondNumber / firstNumber
            inputString[inputString.indexOf("/") - 1] = java.lang.Double.toString(answer)
            inputString.removeAt(inputString.indexOf("/") + 1)
            inputString.removeAt(inputString.indexOf("/"))
        }

        while (inputString.indexOf("-") != -1) {
            val firstNumber = inputString[inputString.indexOf("-") + 1].toDouble()
            val secondNumber = inputString[inputString.indexOf("-") - 1].toDouble()
            val answer = secondNumber - firstNumber
            inputString[inputString.indexOf("-") - 1] = java.lang.Double.toString(answer)
            inputString.removeAt(inputString.indexOf("-") + 1)
            inputString.removeAt(inputString.indexOf("-"))
        }

        return inputString[0]!!.toFloat()
    }

}
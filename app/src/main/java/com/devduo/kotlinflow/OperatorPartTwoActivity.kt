package com.devduo.kotlinflow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class OperatorPartTwoActivity : AppCompatActivity() {
    private val TAG = "OperatorPartTwoActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operator_part_two)

        findViewById<Button>(R.id.btnZipNormal).setOnClickListener {
            doZipNormal()
        }

        findViewById<Button>(R.id.btnZipNotSameSize).setOnClickListener {
            doZipNotSameSize()
        }

        findViewById<Button>(R.id.btnZipWithDelay).setOnClickListener {
            doZipNotSameDelay()
        }

        findViewById<Button>(R.id.btnCombineWithDelay).setOnClickListener {
            doCombineNotSameDelay()
        }

        findViewById<Button>(R.id.btnToStateFlow).setOnClickListener {
            startActivity(
                Intent(
                    this, StateFlowActivity::class.java
                )
            )
        }
    }

    private fun doZipNormal() {
        val intFlow = (1..3).asFlow()
        val strFlow = flowOf("One", "Two", "Three")

        runBlocking {
            intFlow.zip(strFlow) { a, b ->
                "$a -> $b"
            }.collect {
                Log.i(TAG, it)
            }
        }
    }

    private fun doZipNotSameSize() {
        val intFlow = (1..3).asFlow()
        val strFlow = flowOf("One", "Two", "Three", "Four")

        runBlocking {
            intFlow.zip(strFlow) { a, b ->
                "$a -> $b"
            }.collect {
                Log.i(TAG, it)
            }
        }
    }

    private fun doZipNotSameDelay() {
        val intFlow = (1..4).asFlow().onEach { delay(500) }
        val strFlow = flowOf("One", "Two", "Three").onEach { delay(300) }

        runBlocking {
            intFlow.zip(strFlow) { a, b ->
                "$a -> $b"
            }.collect {
                Log.i(TAG, it)
            }
        }
    }

    private fun doCombineNotSameDelay() {
        val intFlow = (1..4).asFlow().onEach { delay(500) }
        val strFlow = flowOf("One", "Two", "Three").onEach { delay(300) }

        runBlocking {
            intFlow.combine(strFlow) { a, b ->
                "$a -> $b"
            }.collect {
                Log.i(TAG, it)
            }
        }
    }
}
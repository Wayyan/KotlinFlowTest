package com.devduo.kotlinflow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class OperatorsPartOneActivity : AppCompatActivity() {
    private val TAG = "OperatorsPartOneActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operators_part_one)

        findViewById<Button>(R.id.btnMapSync).setOnClickListener {
            doMapSync()
        }

        findViewById<Button>(R.id.btnMapSuspend).setOnClickListener {
            doMapSuspend()
        }

        findViewById<Button>(R.id.btnFilterSync).setOnClickListener {
            doFilterSync()
        }

        findViewById<Button>(R.id.btnFilterSuspend).setOnClickListener {
            doFilterSuspend()
        }

        findViewById<Button>(R.id.btnTake).setOnClickListener {
            take()
        }

        findViewById<Button>(R.id.btnTakeWhile).setOnClickListener {
            takeWhile()
        }

        findViewById<Button>(R.id.btnToPartTwo).setOnClickListener {
           startActivity(Intent(this,OperatorPartTwoActivity::class.java))
        }
    }

    private fun doMapSync() {
        runBlocking {
            (0..3).asFlow()
                .map { it ->
                    return@map "response in sync -> $it"
                }
                .collect {
                    Log.i(TAG, it)
                }
        }
    }

    private fun doMapSuspend() {
        runBlocking {
            (0..3).asFlow()
                .map { it ->
                    mapToString(it)
                }
                .collect {
                    Log.i(TAG, it)
                }
        }
    }

    private suspend fun mapToString(num: Int): String {
        delay(300)
        return "response in suspend -> $num"
    }

    private fun doFilterSync() {
        runBlocking {
            (0..10).asFlow()
                .filter {
                    filterEvenSync(it)
                }
                .collect {
                    Log.i(TAG, "doFilterSync $it")
                }
        }
    }

    private fun filterEvenSync(num: Int): Boolean {
        return (num % 2 == 0)
    }

    private fun doFilterSuspend() {
        runBlocking {
            (0..10).asFlow()
                .filter {
                    filterEvenSuspend(it)
                }
                .collect {
                    Log.i(TAG, "doFilterSuspend $it")
                }
        }
    }

    private suspend fun filterEvenSuspend(num: Int): Boolean {
        delay(300)
        return (num % 2 == 0)
    }

    private fun take() {
        runBlocking {
            (0..10).asFlow()
                .take(4)
                .collect {
                    Log.i(TAG, "take $it")
                }
        }
    }

    private fun takeWhile() {
        val startTime = System.currentTimeMillis()
        runBlocking {
            (0..1000).asFlow()
                .takeWhile {
                    System.currentTimeMillis() - startTime < 10
                }
                .collect {
                    Log.i(TAG, "takeWhile $it")
                }
        }
    }
}
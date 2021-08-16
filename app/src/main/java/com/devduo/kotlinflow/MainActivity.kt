package com.devduo.kotlinflow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var fixedFlow: Flow<Int>
    private lateinit var collectionFlow: Flow<Int>
    private lateinit var fixedFlowWithLambda: Flow<Int>
    private lateinit var channelFlow: Flow<Int>

    private var list = listOf(1, 2, 3, 4, 5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpFixedFlow()
        setUpCollectionFlow()
        setUpFixedFlowLambda()
        setUpChannelFlow()

        findViewById<Button>(R.id.btnLaunchFixedFlow).setOnClickListener {
            collectFixedFlow()
        }

        findViewById<Button>(R.id.btnLaunchCollectionFlow).setOnClickListener {
            collectCollectionFlow()
        }

        findViewById<Button>(R.id.btnLaunchLambdaFlow).setOnClickListener {
            collectFixedFlowWithLambda()
        }

        findViewById<Button>(R.id.btnLaunchChannelFlow).setOnClickListener {
            collectChannelFlow()
        }

        findViewById<Button>(R.id.btnLaunchFloChain).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                createFlowChain()
            }
        }

        findViewById<Button>(R.id.btnNext).setOnClickListener {
            startActivity(Intent(this,OperatorsPartOneActivity::class.java))
        }
    }

    private suspend fun createFlowChain() {
        flow {
            (0..10).forEach {
                delay(300)
                Log.i(TAG, "emit ${Thread.currentThread().name}")
                emit(it)
            }
        }.flowOn(Dispatchers.IO)
            .collect {
                Log.i(TAG, "collect ${Thread.currentThread().name}")
                Log.i(TAG, "createFlowChannel $it")
            }
    }

    private fun setUpFixedFlow() {
        fixedFlow = flowOf(1, 2, 3, 4, 5).onEach { delay(300) }
    }

    private fun setUpCollectionFlow() {
        collectionFlow = list.asFlow().onEach { delay(300) }
    }

    private fun setUpFixedFlowLambda() {
        fixedFlowWithLambda = flow {
            (1..5).forEach {
                delay(300)
                emit(it)
            }
        }
    }

    private fun setUpChannelFlow() {
        channelFlow = channelFlow {
            (1..5).forEach {
                delay(300)
                send(it)
            }
        }
    }

    private fun collectFixedFlow() {
        CoroutineScope(Dispatchers.Main).launch {
            fixedFlow.collect {
                Log.i(TAG, "collectFixedFlow $it")
            }
        }
    }

    private fun collectCollectionFlow() {
        CoroutineScope(Dispatchers.Main).launch {
            collectionFlow.collect {
                Log.i(TAG, "collectCollectionFlow $it")
            }
        }
    }

    private fun collectFixedFlowWithLambda() {
        CoroutineScope(Dispatchers.Main).launch {
            fixedFlowWithLambda.collect {
                Log.i(TAG, "collectFixedFlowWithLambda $it")
            }
        }
    }

    private fun collectChannelFlow() {
        CoroutineScope(Dispatchers.Main).launch {
            channelFlow.collect {
                Log.i(TAG, "collectChannelFlow $it")
            }
        }
    }
}
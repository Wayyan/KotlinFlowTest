package com.devduo.kotlinflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StateFlowActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewResult: TextView

    private var dataProvider = DataProvider()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_flow)

        progressBar = findViewById(R.id.progress)
        textViewResult = findViewById(R.id.tvResult)

        findViewById<Button>(R.id.btnNoPrecaution).setOnClickListener {
            startStateFlowWithoutPrecaution()
        }

        findViewById<Button>(R.id.btnPrecaution).setOnClickListener {
            startStateFlowWithPrecaution()
        }
    }

    private fun startStateFlowWithoutPrecaution() {
        progressBar.visibility = View.VISIBLE
        textViewResult.text = ""
        lifecycleScope.launch {
            dataProvider.state.collect {
                progressBar.visibility = View.GONE
                textViewResult.text = it
            }
            dataProvider.getData()
        }
    }

    private fun startStateFlowWithPrecaution() {
        progressBar.visibility = View.VISIBLE
        textViewResult.text = ""
        lifecycleScope.launchWhenStarted {
            dataProvider.state.collect {
                progressBar.visibility = View.GONE
                textViewResult.text = it
            }
            dataProvider.getData()
        }
    }
}
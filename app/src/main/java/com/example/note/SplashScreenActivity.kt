package com.example.note

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        val coroutineScope = CoroutineScope(SupervisorJob()+Dispatchers.Default)
        lifecycleScope.launchWhenCreated {
            delay(4000)
            val intent = Intent(this@SplashScreenActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
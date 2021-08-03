package com.retroblade.hirasawaprod

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvUnder.text = "Hello"
        tvUnder.animateBlink(500L, 2000L) {
            tvCenter.text = "I am Hirasawa"
            tvCenter.animateBlink(500L, 5000L)
            tvUnder.text = "A drawer with more than 4 years of experience"
            tvUnder.animateBlink(2000L, 3500L) {
                tvCenter.text = "I really enjoy drawing"
                tvCenter.animateBlink(500L, 5500L)
                tvUnderLeft.text = "And hope you "
                tvUnderLeft.animateBlink(2500L, 3500L)
                tvUnder.text = "enjoy"
                tvUnder.animateBlink(2500L, 6000L)
                tvUnderRight.text = " my works too"
                tvUnderRight.animateBlink(2500L, 3500L)
            }
        }
    }

}
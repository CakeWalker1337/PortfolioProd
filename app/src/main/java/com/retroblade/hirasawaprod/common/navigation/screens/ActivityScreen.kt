package com.retroblade.hirasawaprod.common.navigation.screens

import android.content.Intent

/**
 * A navigation screen state interface that represents a method for receiving activity intent instance
 */
fun interface ActivityScreen {
    fun getIntent(): Intent
}
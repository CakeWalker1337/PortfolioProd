package com.retroblade.hirasawaprod.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

/**
 * Util class contains methods for work with network
 */
class NetworkManager @Inject constructor(val context: Context) {

    /**
     * Checks if network is available now or not
     * @return network availability
     */
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }

    }
}
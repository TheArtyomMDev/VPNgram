package sds.vpn.gram.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.net.Socket

object NetworkUtils {

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun ping(ip: String, port: Int): Long = withContext(Dispatchers.IO) {
        val startTime = System.currentTimeMillis()

        val pingResult = try {
            withTimeout(5000L) {
                Socket(ip, port)
                val endTime = System.currentTimeMillis()

                endTime - startTime
            }
        } catch (e: Exception) {
            -1
        }

         pingResult
    }
}
package com.hkr.quizme.database_utils

import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL

class APICommunicator {
    companion object {
        val BASE_URL = "http://94.46.48.172:9000"
        val API_KEY = "\$2y\$12\$xohKM8iB6eycMcHTSUnUr.rZN8.bKNdYd4Vgpflq6Mrll34k8gPj6"
    }

    fun apiCallForResponse(apiPath: String, method: String, params: JSONObject): JSONObject {
        val apiURL = URL(BASE_URL + apiPath)
        val connection = apiURL.openConnection() as HttpURLConnection
        try {
            connection.requestMethod = method
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.doOutput = true
            connection.doInput = true
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            Log.d("NETWORK:::", "Sending request...")
            val outputStream = DataOutputStream(connection.outputStream)
            outputStream.use {
                params.put("key", API_KEY)
                outputStream.writeBytes(params.toString())
                outputStream.flush()
            }
            Log.d("NETWORK:::", "Request sent, awaiting response...")
            val responseCode = connection.responseCode
            if (responseCode == 200 || responseCode == 201) {
                val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
                val rawResponse = StringBuilder()
                inputStream.use {
                    var line: String? = inputStream.readLine()
                    while (line != null) {
                        rawResponse.append(line)
                        rawResponse.append('\n')
                        line = inputStream.readLine()
                    }
                }
                connection.disconnect()
                return JSONObject(rawResponse.toString())
            }
            Log.d("NETWORK::::", BASE_URL + apiPath)
            return JSONObject(
                    String.format(
                            "{\"success\": false,\"message\": \"%s\"}",
                            connection.responseMessage
                    )
            )
        } catch (ex: ConnectException) {
            Log.d("DB::::::", ex.toString())
            return JSONObject(
                    "{\"success\": false,\"message\": \"Could not connect to server.\"}"
            )
        } catch (ex: SocketTimeoutException) {
            Log.d("DB::::::", ex.toString())
            return JSONObject(
                    "{\"success\": false,\"message\": \"Could not connect to server.\"}"
            )
        } catch (ex: IOException) {
            Log.d("DB::::::", ex.toString())
            return JSONObject(
                    "{\"success\": false,\"message\": \"Could not connect to server.\"}"
            )
        }
    }
}
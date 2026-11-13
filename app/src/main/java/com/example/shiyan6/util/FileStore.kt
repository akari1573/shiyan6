package com.example.shiyan6.util

import android.content.Context
import android.util.Log
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

object FileStore {

    private const val TAG = "FileStore"

    fun save(context: Context, fileName: String, data: Serializable) {
        try {
            val fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            ObjectOutputStream(fos).use { it.writeObject(data) }
            Log.d(TAG, "save success: $fileName")
        } catch (e: IOException) {
            Log.e(TAG, "save failed: $fileName", e)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Serializable> load(context: Context, fileName: String): T? {
        return try {
            val fis = context.openFileInput(fileName)
            ObjectInputStream(fis).use { it.readObject() as T }
        } catch (e: Exception) {
            Log.d(TAG, "load failed or no data: $fileName", e)
            null
        }
    }
}

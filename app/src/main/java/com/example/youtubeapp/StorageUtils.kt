package com.example.youtubeapp

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.IOException

object StorageUtils {

    // ----------------------------------
    // READ & WRITE STORAGE
    // ----------------------------------
     fun readFile(context: Context, file: File): String {

        val sb = StringBuilder()

        if (file.exists()) {
            try {
                val bufferedReader = file.bufferedReader();
                bufferedReader.useLines { lines ->
                    lines.forEach {
                        sb.append(it)
                        sb.append("\n")
                    }
                }
            } catch (e: IOException) {
                Toast.makeText(
                    context,
                    context.getString(R.string.error_happened),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return sb.toString()
    }

    //
     fun writeFile(context: Context, text: String, file: File) {

        try {
            file.parentFile.mkdirs()
            file.bufferedWriter().use { out ->
                out.write(text)
            }
        } catch (e: IOException) {
            Toast.makeText(context, context.getString(R.string.error_happened), Toast.LENGTH_LONG)
                .show()
            return
        }

        Toast.makeText(context, context.getString(R.string.saved), Toast.LENGTH_LONG).show()
    }
    // ----------------------------------
     // EXTERNAL STORAGE
     // ----------------------------------

     fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
         }

     fun isExternalStorageReadable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state ||
        Environment.MEDIA_MOUNTED_READ_ONLY == state
         }

    fun getFileCount(dirPath: String): ArrayList<String> {
        val list = arrayListOf<String>()
        var count = 0
        val f = File(dirPath)
        val files = f.listFiles()
        if (files != null) for (i in files.indices) {
            count++
            val file = files[i]
            list.add(file.absolutePath)
            if (file.isDirectory) {
                getFileCount(file.absolutePath)
            }
        }
        return list
    }
}
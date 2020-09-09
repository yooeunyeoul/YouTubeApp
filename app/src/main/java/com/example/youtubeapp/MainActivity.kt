package com.example.youtubeapp

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.OutputStream


class MainActivity : AppCompatActivity() {

    // 1 - FILE MANAGEMENT
    private val FILENAME = "tripBook.txt"
    private val FOLDERNAME = "bookTrip"

    val sum: (Int, Int) -> Int = { x, y -> x + y }

    val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }

    val twoParameters: (String, Int) -> String = repeatFun

    var randomCount :Int get() = (0..9).random()
        set(value) {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            camera.captureImage { cameraKitView, bytes ->
//                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//                insertImage(contentResolver,bitmap,""+System.currentTimeMillis(), "")
//                Log.e("TAG", filesDir.toString())
//                createOrGetFile(File(filesDir.toString()), "sampleFile", "sampleDirectory").mkdir()
//                val dd = StorageUtils.readFile(this, File("${filesDir}/sampleDirectory"))

//                val file = File(filesDir, "sampleFile")
//                File.createTempFile("sampleFile",null, cacheDir)
//
//                val cacheFile = File(cacheDir , "sampleFile")
//                if (cacheFile.exists()) {
//                    val ddd = "ddddd"
//                }

                val file = File(getExternalFilesDir(null), "TestFolder")
                if (file.isDirectory()) {
                    File(file, System.currentTimeMillis().toString()).writeBytes(bytes)
                } else {
                    file.mkdirs()
                }

                var count = StorageUtils.getFileCount(file.absolutePath)

                Glide.with(this).load(count[randomCount]).into(capturedImage)





            }
        }



        Logger.d("3".repeatFun(3))

        Logger.d(repeatFun("2", 1))

        Logger.e((twoParameters("3", 2)))

    }


    fun insertImage(
        cr: ContentResolver,
        source: Bitmap?,
        title: String?,
        description: String?
    ): String? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, title)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, title)
        values.put(MediaStore.Images.Media.DESCRIPTION, description)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        // Add the date meta data to ensure the image is added at the front of the gallery
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        var url: Uri? = null
        var stringUrl: String? = null /* value to be returned */
        try {
            url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (source != null) {
                val imageOut: OutputStream? = url?.let { cr.openOutputStream(it) }
                try {
                    source.compress(Bitmap.CompressFormat.JPEG, 50, imageOut)
                } finally {
                    imageOut?.close()
                }
            } else {
                if (url != null) {
                    cr.delete(url, null, null)
                }
                url = null
            }
        } catch (e: Exception) {
            if (url != null) {
                cr.delete(url, null, null)
                url = null
            }
        }
        if (url != null) {
            stringUrl = url.toString()
        }
        return stringUrl
    }

    private fun createFile(mimeType: String, fileName: String) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            // Filter to only show results that can be "opened", such as
            // a file (as opposed to a list of contacts or timezones).
            addCategory(Intent.CATEGORY_OPENABLE)

            // Create a file with the requested MIME type.
            type = mimeType
            putExtra(Intent.EXTRA_TITLE, fileName)
        }

        startActivityForResult(intent, Companion.WRITE_REQUEST_CODE)
    }

    override fun onStart() {
        super.onStart()
        camera.onStart()
    }

    override fun onResume() {
        super.onResume()
        camera.onResume()
    }

    override fun onPause() {
        camera.onPause()
        super.onPause()
    }

    override fun onStop() {
        camera.onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        camera.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun createOrGetFile(destination: File, fileName: String, folderName: String): File {
        val folder = File(destination, folderName)
        // file path = /storage/emulated/0/Android/data/bookTrip/tripBook.txt
        return File(folder, fileName)
    }

    companion object {
        private const val WRITE_REQUEST_CODE: Int = 43
    }
}









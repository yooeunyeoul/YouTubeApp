package com.example.youtubeapp

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import java.io.OutputStream


class MainActivity : AppCompatActivity() {

    val sum: (Int, Int) -> Int = { x, y -> x + y }

    val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }

    val twoParameters: (String, Int) -> String = repeatFun


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            camera.captureImage { cameraKitView, bytes ->
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                insertImage(contentResolver,bitmap,""+System.currentTimeMillis(), "")


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
}


open class Rectangle {
    open fun draw() {

    }
}

interface Polygon {
    fun draw() {

    }
}

abstract class Square() : Rectangle() {
    abstract override fun draw()
}

var counter = 0
    set(value) {
        if (value >= 0) field = value
    }

fun interface KRunnable {
    fun invoke()
}

fun interface Inpredicate {
    fun accept(i: Int): Boolean
}


val isEvent = object : Inpredicate {
    override fun accept(i: Int): Boolean {
        return i % 2 == 0
    }
}

fun main(){
    mutableListOf<Int>(3, 4).swap(0, 1)
}

fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}


open class Shape{
    var houser = 1
}

class Recationgle: Shape()

fun Shape.getName() = "Sahpe"

fun Rectangle.getName() = " Rectangle"

fun Any?.toString(): String{
    if(this == null) return "null"
    return toString()
}

val <T> List<T>.lastIndex : Int
 get() = size-1

val Shape.house: Int
    get() = 2

class Host(val hostname: String)

data class User(val name: String, val age: Int)

val jack = User(name = "ddd", age = 1)
fun ddd(){
    val olderJack = jack.copy(age = 2)
}

val ints : Array<Int> = arrayOf(1, 2, 3)
val any = Array<Any>(3){""}


fun double(x: Int) :Int = x*2

fun <T> singleTOlList(item: T) : List<T>{
    return listOf<T>()
}
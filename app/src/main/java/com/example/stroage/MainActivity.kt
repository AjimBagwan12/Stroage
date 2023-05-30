package com.example.stroage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //External Stroage

        val state = Environment.getExternalStorageState()
        when {
            state.equals(Environment.MEDIA_MOUNTED) ->
                mt("Ext storage is RW")
            else ->
                mt("Ext storage not available")
        }
        mt("emulated? ${Environment.isExternalStorageEmulated()}")

        val extRootDir: File = Environment.getExternalStorageDirectory()
        mt("Ext root dir: ${extRootDir.absolutePath}")

        val bitcodeDir = File(extRootDir, "bitcode")
        if (!bitcodeDir.exists()) {
            mt("dir created " + bitcodeDir.mkdir())

            val appExtDir = getExternalFilesDir(null)
            mt("App ext storage dir: ${appExtDir!!.absolutePath}")
            val bitcodeDir1 = File(appExtDir, "bitcode")
            if (!bitcodeDir1.exists()) {
                mt("app ext dir created" + bitcodeDir1.mkdir())

//Internal storage
                val fout: FileOutputStream =
                    openFileOutput("my_file1.txt", Activity.MODE_PRIVATE or Activity.MODE_APPEND)
                fout.write("this is simple string".toByteArray())
                fout.close()

                val fin: FileInputStream = openFileInput("my_file1.txt")
                val data = ByteArray(200)
                val count = fin.read(data)
                fin.close()
                mt(String(data, 0, count))

                getDir("bitcode", Activity.MODE_PRIVATE)

                com.example.stroage.Settings.setBgColor(this, "Black")
                mt(com.example.stroage.Settings.getBgColor(this))

                val prefs: SharedPreferences =
                    getSharedPreferences("my_prefs", Activity.MODE_PRIVATE)

                var editor: SharedPreferences.Editor = prefs.edit()
                editor.putString("name", "Bitcode")
                editor.putInt("code", 101)
                editor.commit()

                var code = prefs.getInt("code", -1)
                var name = prefs.getString("name", "Not available")

                mt("$name $code")
            }
        }
        fun mt(text: String) {
            Log.e("tag", text)
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun mt(text: String) {
         Log.e("tag", text)
         Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

    }


}










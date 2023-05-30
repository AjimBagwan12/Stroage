package com.example.stroage

import android.content.Context
import java.io.File
import java.io.FileOutputStream

object FileUtil  {
    fun createTempFile(context: Context,filename:String): Boolean {
        val tempFile = File(context.cacheDir, filename)
        if (tempFile.exists()) {
            return false
        }
        return tempFile.createNewFile()
    }

    fun createFileAndWriteData(context: Context,filename: String,data:ByteArray) :Boolean{
        val newFile = File(context.filesDir,filename)

        if (newFile.exists()){
            return false
        }
        if (!newFile.createNewFile()){
            return false
        }
        val fout = FileOutputStream(newFile)
        fout.write(data)
        fout.close()

        return true
    }
}
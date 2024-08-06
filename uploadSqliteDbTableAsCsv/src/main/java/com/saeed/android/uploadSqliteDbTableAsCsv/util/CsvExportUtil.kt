package com.saeed.android.uploadSqliteDbTableAsCsv.util

import android.content.Context
import android.database.Cursor
import android.os.Environment
import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import okio.Buffer
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class CsvExportUtil {


    companion object {
        @Volatile
        private var INSTANCE: CsvExportUtil? = null

        fun getInstance(): CsvExportUtil {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: CsvExportUtil().also { INSTANCE = it }
            }
        }
    }

    fun exportDatabaseToCsv(context: Context, dataCursor: Cursor): File {
        val csvFile =
            File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "receipts.csv")

        var csvWriter: CSVWriter? = null

        try {
            csvWriter = CSVWriter(FileWriter(csvFile))
            var cursor: Cursor? = dataCursor
            csvWriter.writeNext(cursor!!.columnNames)
            while (cursor.moveToNext()) {
                val row = Array(cursor.columnCount) { i -> cursor.getString(i) }
                csvWriter.writeNext(row)
            }

            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                csvWriter?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return csvFile
    }



}
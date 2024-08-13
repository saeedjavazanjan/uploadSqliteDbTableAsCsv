package com.saeed.android.uploadSqliteDbTableAsCsv.main

import android.content.Context
import com.saeed.android.uploadSqliteDbTableAsCsv.util.CsvExportUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import kotlin.reflect.full.memberProperties
import android.database.MatrixCursor
import com.saeed.android.uploadSqliteDbTableAsCsv.network.RetrofitInstance
import com.saeed.android.uploadSqliteDbTableAsCsv.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import org.json.JSONObject

class ConvertAndUploadCsv(
    private val jwtBearerToken: String,
    private val baseUrl: String,
    private val subUrl: String,
    private val dataList: MutableList<Any>,
    private val context:Context
) {

    private val csvExportUtil=CsvExportUtil.getInstance()

    private val apiService = RetrofitInstance.getApiService(baseUrl)



    fun uploadToServer():Flow<DataState<ResponseBody?>> = flow {

        emit(DataState.loading())
        try {
            val multipartBodyPart=convertDatabaseToPart()

            val response = if (jwtBearerToken.isEmpty()) {
                apiService.uploadDatabase(
                    dataBase = multipartBodyPart,
                    url = subUrl
                )
            } else {
                apiService.uploadDatabaseWithAuthorization(
                    token = jwtBearerToken,
                    dataBase = multipartBodyPart,
                    url = subUrl
                )
            }
            if (response.isSuccessful){
                emit(DataState.success(response.body()))
            }else{
                val errorBody = response.errorBody()?.string()
                emit(DataState.error(errorBody!!))

            }

        }catch (e:Exception){
            emit(DataState.error(e.message.toString()))

        }



    }





   private fun convertDatabaseToPart(): MultipartBody.Part{
       if (dataList.isEmpty()) {
           throw IllegalArgumentException("List is empty")
       }

       val dbFile: File = csvExportUtil.
       exportDatabaseToCsv(
           context,
           convertToCursor()
       )
       val requestFile = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), dbFile)
       return MultipartBody.Part.createFormData("DatabaseFile", dbFile.name, requestFile)

   }

    private fun convertToCursor(): MatrixCursor {
        if (dataList.isEmpty()) {
            throw IllegalArgumentException("List is empty")
        }
        val firstItem = dataList.first()
        val columns = getColumnsForItem(firstItem)

        val cursor = MatrixCursor(columns)
        for (item in dataList) {
            val rowData = getRowDataForItem(item, columns)
            cursor.addRow(rowData)
        }
        return cursor
    }
    private fun getColumnsForItem(item: Any): Array<String> {
        return item::class.memberProperties.map { it.name }.toTypedArray()
    }


    private fun getRowDataForItem(item: Any, columns: Array<String>): Array<Any?> {
        return columns.map { column ->
            item::class.memberProperties.find { it.name == column }?.getter?.call(item)
        }.toTypedArray()
    }
}
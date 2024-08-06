package com.saeed.android.uploadSqliteDbTableAsCsv.main

class ConvertAndUploadCsvBuilder {

    private var jwtBearerToken:String=""
    private var baseUrl:String=""
    private var subUrl:String=""
    private var dataList:MutableList<Any> = mutableListOf()


    fun setJwtBearerToken(token:String)=apply {
        this.jwtBearerToken=token
    }
    fun setBaseUrl(baseURL:String)=apply {
        this.baseUrl=baseURL
    }
    fun setSubUrl(subURL:String)=apply {
        this.subUrl=subURL
    }
    fun setDataList(list: MutableList<Any>)=apply {
        this.dataList=list
    }

    fun build():ConvertAndUploadCsv{
        return ConvertAndUploadCsv(
            jwtBearerToken, baseUrl, subUrl, dataList
        )
    }

}
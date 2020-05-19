package com.example.contentProviderApp.repositories

import com.example.contentProviderApp.AppApplication.Companion.retrofit
import com.example.contentProviderApp.network.ApiService

class MainRepositories {
    private val newsApi: ApiService.Main = retrofit!!.create(ApiService.Main::class.java)

    companion object {
        private var newsRepository: MainRepositories? = null
        val instance: MainRepositories?
            get() {
                if (newsRepository == null) {
                    newsRepository = MainRepositories()
                }
                return newsRepository
            }
    }

    fun getDetails(id:String)=newsApi.getUserDetails(id)

}
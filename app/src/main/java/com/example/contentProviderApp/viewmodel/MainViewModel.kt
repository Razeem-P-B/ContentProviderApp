package com.example.contentProviderApp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contentProviderApp.model.ApiResponse
import com.example.contentProviderApp.repositories.MainRepositories
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException


class MainViewModel : ViewModel() {

    var mainRepositories: MainRepositories = MainRepositories.instance!!
    var responseData = MutableLiveData<ApiResponse>()
    var error = MutableLiveData<String>()
    var compositeDisposable = CompositeDisposable()

    fun getDetails(id: String) {
        compositeDisposable.add(
            mainRepositories.getDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    responseData.value = it
                }, { throwable ->
                    if (throwable is HttpException) {
                        val response = throwable.response()
                        val errorResponse: JSONObject?
                        errorResponse = getJsonStringFromResponse(response!!) as JSONObject?
                        if (errorResponse != null) {
                            error.value = errorResponse.optString("message")
                        } else
                            error.value = "Something went wrong"
                    } else {
                        error.value = "Something went wrong"
                    }

                })
        )


    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun getJsonStringFromResponse(response: retrofit2.Response<*>): Any? {
        return try {
            JSONObject(response.errorBody()!!.string())
        } catch (e: Exception) {
            return null
        }

    }

}
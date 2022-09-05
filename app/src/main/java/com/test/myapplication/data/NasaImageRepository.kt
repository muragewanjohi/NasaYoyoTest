package com.test.myapplication.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.test.myapplication.api.NasaImageApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NasaImageRepository @Inject constructor(private val nasaImageApi: NasaImageApi) {

    //fun getSearchResults(query: String): LiveData<PagingData<NasaImageDetails>> =
    fun getSearchResults(query: String)=
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NasaImagesPagingSource(nasaImageApi, query) }
        ).liveData
}
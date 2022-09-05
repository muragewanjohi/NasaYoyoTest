package com.test.myapplication.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.test.myapplication.api.NasaImageApi
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class NasaImagesPagingSource(
    private val nasaImageApi: NasaImageApi,
    private val query: String
) : PagingSource<Int, NasaImageDetails>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NasaImageDetails> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        val q = "milky way"
        val mediaType = "image"
        val yearStart = "2017"
        val yearEnd = "2017"

        return try {

            val photos: MutableList<NasaImageDetails> = mutableListOf()

            val response = nasaImageApi.searchPhotos( q,mediaType,yearStart,yearEnd,position)

            Log.d("response", response.toString())

            val jsonObject = JSONObject(Gson().toJson(response.body()))
            val collection = jsonObject.getJSONObject("collection")
            val itemsArray: JSONArray = collection.getJSONArray("items")

            for (i in 0 until itemsArray.length()) {
                val itemsObject: JSONObject = itemsArray.getJSONObject(i)
                val dataArray: JSONArray = itemsObject.getJSONArray("data")
                val dataObject: JSONObject = dataArray.getJSONObject(0)
                val description = dataObject.getString("description")
                val title = dataObject.getString("title")
                val dateCreated = dataObject.getString("date_created")
                val center = dataObject.getString("center")
                val linksArray: JSONArray = itemsObject.getJSONArray("links")
                val linksObject: JSONObject = linksArray.getJSONObject(0)
                val linksHref = linksObject.getString("href")

                photos.add(NasaImageDetails(title, center, description, dateCreated, linksHref))
            }

            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NasaImageDetails>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
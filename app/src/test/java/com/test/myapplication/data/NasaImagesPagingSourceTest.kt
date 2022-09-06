package com.test.myapplication.data

import androidx.paging.PagingSource
import com.test.myapplication.api.NasaImageApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import org.junit.Test
const val DEFAULT_QUERY = "milky way"

@ExperimentalCoroutinesApi
internal class NasaImagesPagingSourceTest {

    @Mock
    lateinit var mockApi: NasaImageApi

    private lateinit var nasaImagesPagingSource: NasaImagesPagingSource

    private val mockNasaImages = listOf(
        NasaImageDetails("title1","center1","description1","dateCreated1","href1"),
        NasaImageDetails("title12","center12","description12","dateCreated12","href12"),
        NasaImageDetails("title13","center13","description13","dateCreated13","href13")
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        nasaImagesPagingSource = NasaImagesPagingSource(mockApi,"milky way")
    }

    @Test
    fun `nasa images paging source load - failure - http error`() = runTest {

        val q = ""
        val mediaType = ""
        val yearStart = ""
        val yearEnd = ""
        val page = 1

        val error = RuntimeException("404", Throwable())
        given(mockApi.searchPhotos(q,mediaType,yearStart,yearEnd,page)).willThrow(error)

        val expectedResult = PagingSource.LoadResult.Error<Int, NasaImageDetails>(error)

        assertEquals(
            expectedResult, nasaImagesPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )

    }

    @Test
    fun `nasa images paging source load - failure - received null`() = runTest {
        val q = ""
        val mediaType = ""
        val yearStart = ""
        val yearEnd = ""
        val page = 1

        given(mockApi.searchPhotos(q,mediaType,yearStart,yearEnd,page)).willReturn(null)

        val expectedResult = PagingSource.LoadResult.Error<Int, NasaImageDetails>(NullPointerException())

        assertEquals(
            expectedResult.toString(), nasaImagesPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }

    @Test
// Since load is a suspend function, runTest is used to ensure that it
// runs on the test thread.
    fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runTest {
        val pagingSource = NasaImagesPagingSource(mockApi, DEFAULT_QUERY)
        assertEquals(
            PagingSource.LoadResult.Page(
                data = mockNasaImages,
                prevKey = mockNasaImages[0].href,
                nextKey = mockNasaImages[1].href
            ),
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            ),
        )
    }

    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfPageKeyedData() = runTest {
        val pagingSource = NasaImagesPagingSource(mockApi, DEFAULT_QUERY)
        assertEquals(
            PagingSource.LoadResult.Page(
                data = mockNasaImages,
                prevKey = null,
                nextKey = mockNasaImages[1].href
            ).toString(),
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            ),
        )
    }



}
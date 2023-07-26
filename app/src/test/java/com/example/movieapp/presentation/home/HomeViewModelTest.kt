package com.example.movieapp.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieapp.common.MoviesListType
import com.example.movieapp.common.Resource
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MoviesList
import com.example.movieapp.domain.use_case.GetMoviesListUseCase
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.TestScheduler
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule


@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Rule @JvmField var testSchedulerRule = RxImmediateSchedulerRule()


    @Mock
    private lateinit var getMoviesListUseCase: GetMoviesListUseCase


    // The ViewModel instance to be tested
    private lateinit var homeViewModel: HomeViewModel

    private val testScheduler = TestScheduler()

    private val testMovie = Movie(
        id = 1,
        adult = false,
        backdropPath =  "",
        genres = listOf(),
        originalLanguage =  "English",
        overview = "Overview",
        posterPath = "/rktDFPbfHfUbArZ6OOOKsXcv0Bm.jpg",
        releaseDate =  "2023-06-13",
        title = "Title",
        voteAverage = 6.9,
        imdbUrl = "",
        revenue = null,
        tagline = null,
        productionCompanies = null
    )



    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this);

        getMoviesListUseCase = Mockito.mock(GetMoviesListUseCase::class.java)

        homeViewModel = HomeViewModel(getMoviesListUseCase)

    }


    @Test
    fun testSetMoviesListType() {
        val listType = MoviesListType.POPULAR

        homeViewModel.setMoviesListType(listType)

        val observedListType = homeViewModel.moviesListType.value
        assertEquals(listType, observedListType)

    }

    @Test
    fun `get_PopularMovies_Success`() {
        // Arrange
        val mockMoviesList = MoviesList(1, listOf(testMovie),10)
        val observable = Flowable.just(Resource.success(mockMoviesList))
            .subscribeOn(testScheduler)
            .observeOn(testScheduler)
        `when`(getMoviesListUseCase.invoke(MoviesListType.POPULAR)).thenReturn(observable)

        // Act
        homeViewModel.getPopularMoviesList()
        testScheduler.triggerActions()

        // Assert
        val liveDataValue = homeViewModel.popularMoviesList.value
        assertNotNull(liveDataValue)
        assertTrue(liveDataValue?.status == Resource.Status.SUCCESS)
        assertEquals(mockMoviesList, liveDataValue?.data)
    }

    @Test
    fun `get_nowPlayingMovies_Success`() {
        // Arrange
        val mockMoviesList = MoviesList(1, listOf(testMovie),10)
        val observable = Flowable.just(Resource.success(mockMoviesList))
            .subscribeOn(testScheduler)
            .observeOn(testScheduler)
        `when`(getMoviesListUseCase.invoke(MoviesListType.NOW_PLAYING)).thenReturn(observable)

        // Act
        homeViewModel.getPopularMoviesList()
        testScheduler.triggerActions()

        // Assert
        val liveDataValue = homeViewModel.nowPlayingMoviesList.value
        assertNotNull(liveDataValue)
        assertTrue(liveDataValue?.status == Resource.Status.SUCCESS)
        assertEquals(mockMoviesList, liveDataValue?.data)
    }

    @Test
    fun `get_upcomingMovies_Success`() {
        // Arrange
        val mockMoviesList = MoviesList(1, listOf(testMovie),10)
        val observable = Flowable.just(Resource.success(mockMoviesList))
            .subscribeOn(testScheduler)
            .observeOn(testScheduler)
        `when`(getMoviesListUseCase.invoke(MoviesListType.UPCOMING)).thenReturn(observable)

        // Act
        homeViewModel.getPopularMoviesList()
        testScheduler.triggerActions()

        // Assert
        val liveDataValue = homeViewModel.upcomingMoviesList.value
        assertNotNull(liveDataValue)
        assertTrue(liveDataValue?.status == Resource.Status.SUCCESS)
        assertEquals(mockMoviesList, liveDataValue?.data)
    }

    @Test
    fun `get_topRatedMovies_Success`() {
        // Arrange
        val mockMoviesList = MoviesList(1, listOf(testMovie),10)
        val observable = Flowable.just(Resource.success(mockMoviesList))
            .subscribeOn(testScheduler)
            .observeOn(testScheduler)
        `when`(getMoviesListUseCase.invoke(MoviesListType.TOP_RATED)).thenReturn(observable)

        // Act
        homeViewModel.getPopularMoviesList()
        testScheduler.triggerActions()

        // Assert
        val liveDataValue = homeViewModel.topRatedMoviesList.value
        assertNotNull(liveDataValue)
        assertTrue(liveDataValue?.status == Resource.Status.SUCCESS)
        assertEquals(mockMoviesList, liveDataValue?.data)
    }


    @Test
    fun `get_PopularMovies_Error`() {
        // Arrange
        val errorMsg = "An error occurred"
        val observable = Flowable.just(Resource.error<MoviesList>(null, errorMsg))
            .subscribeOn(testScheduler)
            .observeOn(testScheduler)
        `when`(getMoviesListUseCase.invoke(MoviesListType.POPULAR)).thenReturn(observable)

        // Act
        homeViewModel.getPopularMoviesList ()
        testScheduler.triggerActions()

        // Assert
        val liveDataValue = homeViewModel.popularMoviesList.value
        assertNotNull(liveDataValue)
        assertTrue(liveDataValue?.status == Resource.Status.ERROR)
        assertEquals(errorMsg, liveDataValue?.message)
    }


    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }

}



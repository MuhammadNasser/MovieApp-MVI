package com.muhammad.movieappmvi.di

import androidx.room.Room
import com.muhammad.movieappmvi.core.NetworkChecker
import com.muhammad.movieappmvi.core.NetworkCheckerImpl
import com.muhammad.movieappmvi.data.local.db.AppDatabase
import com.muhammad.movieappmvi.data.local.source.MovieLocalDataSourceImpl
import com.muhammad.movieappmvi.data.remote.api.MovieApiService
import com.muhammad.movieappmvi.data.remote.source.MovieRemoteDataSourceImpl
import com.muhammad.movieappmvi.data.repository.MovieRepositoryImpl
import com.muhammad.movieappmvi.data.repository.MovieRepository
import com.muhammad.movieappmvi.data.local.source.MovieLocalDataSource
import com.muhammad.movieappmvi.data.remote.source.MovieRemoteDataSource
import com.muhammad.movieappmvi.domain.usecase.GetMovieDetailsUseCase
import com.muhammad.movieappmvi.domain.usecase.GetMoviesUseCase
import com.muhammad.movieappmvi.domain.usecase.ToggleFavoriteUseCase
import com.muhammad.movieappmvi.presentation.details.MovieDetailsViewModel
import com.muhammad.movieappmvi.presentation.home.HomeViewModel
import com.muhammad.movieappmvi.utils.Constants.BASE_URL
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single<NetworkChecker> { NetworkCheckerImpl(context = get()) }

    single {
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .pingInterval(10, TimeUnit.SECONDS)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(MovieApiService::class.java) }
}


val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "movie_db"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<AppDatabase>().movieDao() }
    single { get<AppDatabase>().cachedMovieDao() }
}

val dataSourceModule = module {
    single<MovieRemoteDataSource> { MovieRemoteDataSourceImpl(api = get()) }
    single<MovieLocalDataSource> { MovieLocalDataSourceImpl(movieDao = get(), cachedMovieDao = get()) }
}


val repositoryModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get(),
            networkChecker = get()
        )
    }
}

val useCaseModule = module {
    factory { GetMoviesUseCase(repository = get()) }
    factory { ToggleFavoriteUseCase(repository = get()) }
    factory { GetMovieDetailsUseCase(repository = get()) }
}

@ExperimentalCoroutinesApi
val viewModelsModule = module {
    viewModel {
        HomeViewModel(
            getMoviesUseCase = get(), toggleFavoriteUseCase = get()
        )
    }

    viewModel{
        MovieDetailsViewModel(
            getMovieDetailsUseCase = get(), toggleFavoriteUseCase = get()
        )
    }
}
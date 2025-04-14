package com.muhammad.movieappmvi.core

import android.app.Application
import com.muhammad.movieappmvi.di.dataSourceModule
import com.muhammad.movieappmvi.di.databaseModule
import com.muhammad.movieappmvi.di.networkModule
import com.muhammad.movieappmvi.di.repositoryModule
import com.muhammad.movieappmvi.di.useCaseModule
import com.muhammad.movieappmvi.di.viewModelsModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

@ExperimentalCoroutinesApi
class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            printLogger()
            androidContext(this@MovieApp)
            modules(
                networkModule,
                databaseModule,
                dataSourceModule,
                repositoryModule,
                useCaseModule,
                viewModelsModule
            )
        }
    }
}
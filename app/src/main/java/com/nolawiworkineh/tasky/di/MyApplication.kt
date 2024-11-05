package com.nolawiworkineh.tasky.di

import android.app.Application
import com.nolawiworkineh.core.data.BuildConfig
import com.nolawiworkineh.tasky.logging.TimberDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(TimberDebugTree())
        }
    }
}

package com.plus.simplefylist

import android.app.Application
import com.plus.simplefylist.di.appModule
import com.plus.simplefylist.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SimplefyListAplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SimplefyListAplication)
            modules(appModule, databaseModule)
        }
    }
}
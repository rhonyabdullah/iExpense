package com.mobile.iexpense

import android.app.Application
import com.mobile.iexpense.di.initKoin
import org.koin.android.ext.koin.androidContext

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AppApplication)
        }
    }
}

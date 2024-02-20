package com.disney.interview.application

import android.app.Application
import com.disney.framework.http.configuration.ClientConfiguration
import com.disney.framework.http.provider.DisneyApiClientProvider
import com.disney.interview.BuildConfig

class DisneyInterviewApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // build client configuration
        val clientConfiguration = ClientConfiguration.Builder()
            .setApiKeyPublic(BuildConfig.API_KEY_PUBLIC)
            .setApiKeyPrivate(BuildConfig.API_KEY_PRIVATE)
            .create()

        // initialize API client provider
        DisneyApiClientProvider.initialize(
            applicationContext,
            clientConfiguration
        )
    }
}

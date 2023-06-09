package com.iheke.ispy.di

import android.app.Application
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.*
import com.iheke.ispy.challenges.data.api.ISpyService
import com.iheke.ispy.challenges.data.permission.PermissionService
import com.iheke.ispy.challenges.data.repositories.permissions.PermissionServiceImpl
import com.iheke.ispy.challenges.data.location.LocationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Dagger module for providing dependencies related to the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Provides an instance of the ISpyService.
     *
     * @return An instance of the ISpyService.
     */
    @Provides
    fun provideUserService(): ISpyService {
        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ISpyService::class.java)
    }

    /**
     * Provides an instance of the PermissionService.
     *
     * @param app The application instance.
     * @return An instance of the PermissionService.
     */
    @Provides
    fun providePermissionService(app: Application): PermissionService {
        return PermissionServiceImpl()
    }

    /**
     * Provides an instance of the FusedLocationProviderClient.
     *
     * @param context The context.
     * @return An instance of the FusedLocationProviderClient.
     */
    @Provides
    fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    /**
     * Provides an instance of the LocationProvider.
     *
     * @param context The context.
     * @return An instance of the LocationProvider.
     */
    @Provides
    fun provideLocationProvider(context: Context): LocationProvider {
        return LocationProvider(context)
    }

    /**
     * Provides the application context.
     *
     * @param application The application instance.
     * @return The application context.
     */
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}


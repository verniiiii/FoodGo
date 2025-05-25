package com.example.foodgo.di

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.foodgo.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext appContext: Context): PreferencesManager {
        return PreferencesManager(appContext)
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface PreferencesEntryPoint {
    fun preferencesManager(): PreferencesManager
}

@Composable
inline fun <reified T : Any> hiltEntryPoint(): T {
    val context = LocalContext.current.applicationContext
    return EntryPointAccessors.fromApplication(context, T::class.java)
}

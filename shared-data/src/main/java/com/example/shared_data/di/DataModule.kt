package com.example.shared_data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.shared_data.RepositoryImpl
import com.example.shared_data.database.UserDao
import com.example.shared_data.database.UserDatabase
import com.example.shared_data.preferences.SharedPref
import com.example.shared_data.utils.Constants.Companion.SHARED_PREFERENCES_NAME
import com.example.shared_domain.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideRepository(sharedPref: SharedPref, userDao: UserDao): Repository =
        RepositoryImpl(sharedPref, userDao)

    @Provides
    fun provideSharedPref(sharedPreferences: SharedPreferences): SharedPref =
        SharedPref(sharedPreferences)

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao = userDatabase.getUserDao()

    @Provides
    fun provideUserDatabase(context: Context): UserDatabase =
        UserDatabase.getUserDatabase(context)
}
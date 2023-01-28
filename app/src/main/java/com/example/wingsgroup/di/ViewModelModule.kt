package com.example.wingsgroup.di

import android.content.Context
import com.example.wingsgroup.data.UserRepository
import com.example.wingsgroup.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideUserRepository(
        userDao: UserDao,
        @ApplicationContext context: Context
    ): UserRepository = UserRepository(userDao, context)
}
package com.nolawiworkineh.auth.data.di

import com.nolawiworkineh.auth.data.EmailPatternValidator
import com.nolawiworkineh.auth.data.NamePatternValidator
import com.nolawiworkineh.auth.domain.PatternValidator
import com.nolawiworkineh.auth.domain.UserDataValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideEmailPatternValidator(): PatternValidator = EmailPatternValidator


    @Provides
    @Singleton
    fun provideNamePatternValidator(): PatternValidator = NamePatternValidator


    @Provides
    @Singleton
    fun provideUserDataValidator(
        emailPatternValidator: PatternValidator,
        namePatternValidator: PatternValidator
    ): UserDataValidator {
        return UserDataValidator(emailPatternValidator, namePatternValidator)
    }
}

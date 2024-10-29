package com.nolawiworkineh.auth.data.di

import com.nolawiworkineh.auth.data.AuthApiService
import com.nolawiworkineh.auth.data.AuthRepositoryImpl
import com.nolawiworkineh.auth.data.EmailPatternValidator
import com.nolawiworkineh.auth.data.NamePatternValidator
import com.nolawiworkineh.auth.domain.AuthRepository
import com.nolawiworkineh.auth.domain.PatternValidator
import com.nolawiworkineh.auth.domain.UserDataValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EmailValidator

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NameValidator

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @EmailValidator
    @Provides
    fun provideEmailPatternValidator(): PatternValidator = EmailPatternValidator


    @NameValidator
    @Provides
    fun provideNamePatternValidator(): PatternValidator = NamePatternValidator

    @Provides
    @Singleton
    fun provideUserDataValidator(
        @EmailValidator emailPatternValidator: PatternValidator,
        @NameValidator namePatternValidator: PatternValidator
    ): UserDataValidator {
        return UserDataValidator(emailPatternValidator, namePatternValidator)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApiService: AuthApiService
    ): AuthRepository {
        return AuthRepositoryImpl(authApiService)
    }
}

package com.example.matchuphistory.viewmodel

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.matchuphistory.model.MainRepository
import com.example.matchuphistory.util.Injection

class ViewModelFactory private constructor(private val mainRepository: MainRepository, private val applicationContext: Context) :
        ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(MainViewModel::class.java) ->
                        MainViewModel(applicationContext, mainRepository)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T


    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: ViewModelFactory(
                            Injection.provideMainRepository(application.applicationContext),
                            application.applicationContext)
                            .also { INSTANCE = it }
                }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
package com.plus.simplefylist.di

import androidx.room.Room
import com.plus.simplefylist.database.AppDatabase
import com.plus.simplefylist.ui.view.viewModel.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::ProductListViewModel)
    viewModelOf(::CadastroProdutoViewModel)

}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "appDatabase.db"
        ).build()
    }
    single { get<AppDatabase>().productDao() }
    single { get<AppDatabase>().listDao() }
}
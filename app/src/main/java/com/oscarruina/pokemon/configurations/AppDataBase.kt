package com.oscarruina.pokemon.configurations

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oscarruina.pokemon.dao.IUserDao
import com.oscarruina.pokemon.models.User

@Database(entities = [User::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun UserDao() : IUserDao

    companion object{
        private var INSTANCE: AppDataBase? = null
        fun getDatabase(context: Context): AppDataBase{
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppDataBase::class.java,"users_database")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}
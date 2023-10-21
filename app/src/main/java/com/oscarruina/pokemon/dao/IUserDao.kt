package com.oscarruina.pokemon.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.oscarruina.pokemon.models.User

@Dao
interface IUserDao {

    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM table_users AS u WHERE u.username = :username")
    fun getUserByUsername(username: String) : User

}
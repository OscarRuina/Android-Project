package com.oscarruina.pokemon.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_users")
data class User(
    @ColumnInfo(name = "name")var name: String,
    @ColumnInfo(name = "last_name")var lastname: String,
    @ColumnInfo(name = "email")var email: String,
    @ColumnInfo(name = "username")var username: String,
    @ColumnInfo(name = "password")var password: String,
    @ColumnInfo(name = "active")var active: Boolean,
){
    @PrimaryKey(autoGenerate = true) var idUser: Int = 0
}

package com.example.wingsgroup.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_user")
data class UserModel(
    @PrimaryKey
    val email: String,
    val username: String,
    val password: String,
    val phone: String,
)
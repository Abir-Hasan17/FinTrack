package com.learning.fintrack.data.account

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val balance: Double,
    val currency: String,
    val description: String,
    val dateCreated: Long,
    val isActive: Boolean = true
)
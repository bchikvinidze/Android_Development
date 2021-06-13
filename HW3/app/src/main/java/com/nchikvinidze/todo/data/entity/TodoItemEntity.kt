package com.nchikvinidze.todo.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoItemEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "itemName") val itemName: String,
    @ColumnInfo(name = "pinned") val pinned: Boolean
)
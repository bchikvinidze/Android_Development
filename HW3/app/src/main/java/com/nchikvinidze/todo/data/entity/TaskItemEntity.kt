package com.nchikvinidze.todo.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskItemEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val todoItemId: Long,
    @ColumnInfo val value: String = "default_value",
    @ColumnInfo val checked: Boolean = false
)
package com.nchikvinidze.todo.second

import com.nchikvinidze.todo.data.entity.TaskItemEntity
import kotlin.properties.Delegates

data class AsyncTaskParams(val todoName : String, val isPinned : Boolean,
                           val checkedList : ArrayList<String>, val uncheckedList : ArrayList<String>)
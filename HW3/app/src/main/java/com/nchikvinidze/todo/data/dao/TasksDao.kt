package com.nchikvinidze.todo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nchikvinidze.todo.data.entity.TaskItemEntity
import com.nchikvinidze.todo.data.entity.TodoItemEntity

@Dao
interface TasksDao {
    @Query("Select * from TaskItemEntity")
    fun getTasks(): List<TaskItemEntity>

    @Insert
    fun addTask(todo: TaskItemEntity)

    @Query("Delete from TaskItemEntity where todoItemId = :todoItemId")
    fun deleteTask(todoItemId: Long)

    @Query("Select * from TaskItemEntity where todoItemId = :todoId")
    fun getTasksDetails(todoId: Int): List<TaskItemEntity>
}
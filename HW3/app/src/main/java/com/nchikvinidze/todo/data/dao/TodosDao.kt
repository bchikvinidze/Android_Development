package com.nchikvinidze.todo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nchikvinidze.todo.data.entity.TaskItemEntity
import com.nchikvinidze.todo.data.entity.TodoItemEntity

@Dao
interface TodosDao {
    @Query("Select * from TodoItemEntity")
    fun getTodos(): List<TodoItemEntity>

    @Insert
    fun addTodo(todo: TodoItemEntity) : Long

    @Query("Delete from TodoItemEntity where id = :todoId")
    fun deleteTodo(todoId: Long)

    @Query("Select * from TodoItemEntity where id = :todoId")
    fun getTodoDetails(todoId: Long): TodoItemEntity

    @Query("Select * from TaskItemEntity where todoItemId = :todoId")
    fun getTodoTasks(todoId: Long): List<TaskItemEntity>

    @Query("Select * from TodoItemEntity where itemName like '%' || :todoName || '%'")
    fun getTodoFilteredDetails(todoName: String): List<TodoItemEntity>
}
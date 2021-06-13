package com.nchikvinidze.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nchikvinidze.todo.data.dao.TasksDao
import com.nchikvinidze.todo.data.dao.TodosDao
import com.nchikvinidze.todo.data.entity.TaskItemEntity
import com.nchikvinidze.todo.data.entity.TodoItemEntity

@Database(entities = arrayOf(TodoItemEntity::class, TaskItemEntity::class), version = 1, exportSchema = false)
abstract class TodoDatabase() : RoomDatabase() {
    abstract fun todosDao(): TodosDao
    abstract fun tasksDao(): TasksDao

    companion object{
        private val dbName = "db"
        private lateinit var INSTANTCE: TodoDatabase

        fun getInstance(context: Context?): TodoDatabase{
            if(!::INSTANTCE.isInitialized){
                createDatabase(context!!)
            }
            return INSTANTCE
        }

        fun createDatabase(context: Context){
            INSTANTCE = Room.databaseBuilder(context, TodoDatabase::class.java, dbName).build()
        }
    }
}
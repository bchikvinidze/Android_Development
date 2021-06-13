package com.nchikvinidze.todo.main

import com.nchikvinidze.todo.data.entity.TaskItemEntity
import com.nchikvinidze.todo.data.entity.TodoItemEntity

//es unda davaextendebinot main activityebs
interface IMainView {
    fun displayTodos(todos: ArrayList< Pair<TodoItemEntity, List<TaskItemEntity> > >)
}
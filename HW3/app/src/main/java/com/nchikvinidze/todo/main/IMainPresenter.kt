package com.nchikvinidze.todo.main

import com.nchikvinidze.todo.data.entity.TaskItemEntity
import com.nchikvinidze.todo.data.entity.TodoItemEntity

interface IMainPresenter {
    abstract fun onTodoListFetched(todos: ArrayList<Pair<TodoItemEntity, List<TaskItemEntity> > >)
}
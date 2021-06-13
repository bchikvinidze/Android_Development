package com.nchikvinidze.todo.second

import android.content.Context
import android.view.View
import android.widget.EditText
import com.nchikvinidze.todo.data.entity.TaskItemEntity

interface ISecondaryPresenter {
    fun onTodoAdded(todo : View)
    fun onTaskListFetched(tasks : ArrayList<TaskItemEntity>)
    fun getTodoTasks(todoid : String)
}
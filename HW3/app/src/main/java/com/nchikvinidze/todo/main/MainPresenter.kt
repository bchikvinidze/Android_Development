package com.nchikvinidze.todo.main

import com.nchikvinidze.todo.data.entity.TaskItemEntity
import com.nchikvinidze.todo.data.entity.TodoItemEntity

class MainPresenter(var view: IMainView?): IMainPresenter {
    private val interactor = MainInteractor(this)
    var todoList = ArrayList<Pair<TodoItemEntity, List<TaskItemEntity> > >()

    fun getTodos() {
        interactor.getTodosListFromDatabase()
    }

    fun getFilteredTodos(text : String) {
        interactor.getFilteredTodosListFromDatabase(text)
    }

    override fun onTodoListFetched(todos: ArrayList<Pair<TodoItemEntity, List<TaskItemEntity> > >) {
        todoList = todos
        view?.displayTodos(todos)
    }

    fun detachView() {
        view = null
    }
}
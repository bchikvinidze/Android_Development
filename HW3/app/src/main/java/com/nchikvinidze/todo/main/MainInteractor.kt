package com.nchikvinidze.todo.main

import android.os.AsyncTask
import com.nchikvinidze.todo.data.TodoDatabase
import com.nchikvinidze.todo.data.entity.TaskItemEntity
import com.nchikvinidze.todo.data.entity.TodoItemEntity

class MainInteractor(val presenter: IMainPresenter) {

    fun getTodosListFromDatabase() {
        GetTodosTask(presenter).execute()
    }

    fun getFilteredTodosListFromDatabase(text : String){
        GetFilteredTodosTask(presenter).execute(text)
    }

    class GetTodosTask(val presenter: IMainPresenter): AsyncTask<Void, Void, ArrayList<Pair<TodoItemEntity, List<TaskItemEntity>>>>(){
        override fun doInBackground(vararg params: Void?): ArrayList<Pair<TodoItemEntity, List<TaskItemEntity>>> {
            var todosDao = TodoDatabase.getInstance(null).todosDao()
            var tasksDao = TodoDatabase.getInstance(null).tasksDao()
            var todoList = todosDao.getTodos()
            var result = ArrayList<Pair<TodoItemEntity, List<TaskItemEntity>>>()
            var index = 0
            for(todo in todoList){
                result.add(Pair(todo, tasksDao.getTasksDetails(todo.id)))
                index += 1
            }
            return result
        }

        override fun onPostExecute(result: ArrayList<Pair<TodoItemEntity, List<TaskItemEntity>>>?) {
            super.onPostExecute(result)
            if(result != null) {
                presenter.onTodoListFetched(result)
            }
        }
    }

    class GetFilteredTodosTask(val presenter: IMainPresenter): AsyncTask<String, Void, ArrayList<Pair<TodoItemEntity, List<TaskItemEntity>>>>(){
        override fun doInBackground(vararg params: String): ArrayList<Pair<TodoItemEntity, List<TaskItemEntity>>> {
            var text = params[0]
            var todosDao = TodoDatabase.getInstance(null).todosDao()
            var tasksDao = TodoDatabase.getInstance(null).tasksDao()
            var todoList = todosDao.getTodoFilteredDetails(text)
            var result = ArrayList<Pair<TodoItemEntity, List<TaskItemEntity>>>()
            var index = 0
            for(todo in todoList){
                result.add(Pair(todo, tasksDao.getTasksDetails(todo.id)))
                index += 1
            }
            return result
        }

        override fun onPostExecute(result: ArrayList<Pair<TodoItemEntity, List<TaskItemEntity>>>?) {
            super.onPostExecute(result)
            if(result != null) {
                presenter.onTodoListFetched(result)
            }
        }
    }
}
package com.nchikvinidze.todo.second

import android.os.AsyncTask
import com.nchikvinidze.todo.data.TodoDatabase
import com.nchikvinidze.todo.data.entity.TaskItemEntity
import com.nchikvinidze.todo.data.entity.TodoItemEntity

class SecondaryInteractor(val presenter: SecondPresenter) {
    fun insertTasksToDatabase(params: AsyncTaskParams){
        InsertTodo(presenter).execute(params)
    }

    fun getTodoFromDatabase(todoid : String){
        GetTodoFromDatabase(presenter).execute(todoid)
    }

    fun deleteTodo(todoid : String){
        DeleteTodoFromDatabase(presenter).execute(todoid)
    }

    class InsertTodo(val presenter: SecondPresenter): AsyncTask<AsyncTaskParams, Void, Int>(){
        override fun doInBackground(vararg params: AsyncTaskParams) : Int {
            var todosDao = TodoDatabase.getInstance(null).todosDao()
            var tasksDao = TodoDatabase.getInstance(null).tasksDao()
            var id = todosDao.addTodo(TodoItemEntity(itemName = params[0].todoName, pinned = params[0].isPinned))
            for(i in 1..params[0].checkedList.size){
                tasksDao.addTask(TaskItemEntity(todoItemId = id, value = params[0].checkedList[i-1], checked =  true))
            }
            for(i in 1..params[0].uncheckedList.size){
                var task = TaskItemEntity(todoItemId = id, value = params[0].uncheckedList[i-1], checked =  false)
                tasksDao.addTask(task)
            }
            return 0
        }

        override fun onPostExecute(result: Int) {
            super.onPostExecute(result)
        }
    }

    class GetTodoFromDatabase(val presenter: SecondPresenter): AsyncTask<String, Void, Pair<TodoItemEntity, ArrayList<TaskItemEntity>>>(){
        override fun doInBackground(vararg params: String) : Pair<TodoItemEntity, ArrayList<TaskItemEntity>> {
            var id = params[0].toLong()
            var todosDao = TodoDatabase.getInstance(null).todosDao()
            var taskList = todosDao.getTodoTasks(id)
            var tasks = ArrayList<TaskItemEntity>()
            for(item in taskList){
                tasks.add(item)
            }
            var todoEntity = todosDao.getTodoDetails(id)
            var result = Pair(todoEntity, tasks)
            return result
        }

        override fun onPostExecute(result: Pair<TodoItemEntity, ArrayList<TaskItemEntity>>) {
            super.onPostExecute(result)
            presenter.tasksList = result.second
            presenter.todoItemEntity = result.first
            presenter.updateView()
        }
    }

    class DeleteTodoFromDatabase(val presenter: SecondPresenter): AsyncTask<String, Void, Int>(){
        override fun doInBackground(vararg params: String) : Int {
            var id = params[0].toLong()
            var todosDao = TodoDatabase.getInstance(null).todosDao()
            var tasksDao = TodoDatabase.getInstance(null).tasksDao()
            todosDao.deleteTodo(id)
            tasksDao.deleteTask(id)
            return 0
        }

        override fun onPostExecute(result: Int) {
            super.onPostExecute(result)
        }
    }
}
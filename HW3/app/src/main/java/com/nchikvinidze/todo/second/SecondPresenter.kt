package com.nchikvinidze.todo.second

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.nchikvinidze.todo.R
import com.nchikvinidze.todo.data.TodoDatabase
import com.nchikvinidze.todo.data.entity.TaskItemEntity
import com.nchikvinidze.todo.data.entity.TodoItemEntity
import com.nchikvinidze.todo.main.IMainPresenter
import com.nchikvinidze.todo.main.IMainView
import com.nchikvinidze.todo.main.MainInteractor
import java.io.ByteArrayInputStream

class SecondPresenter(var view: NewTodoActivity?, var db : TodoDatabase) : ISecondaryPresenter {
    private val interactor = SecondaryInteractor(this) // dbs aqedan unda daukavshirde
    var tasksList = ArrayList<TaskItemEntity>()
    var todoItemEntity = TodoItemEntity(id = -1, itemName = "", pinned = false) // placeholder

    override fun onTaskListFetched(tasks: ArrayList<TaskItemEntity>) {

    }

    override fun onTodoAdded(todo : View) {
        var view = (todo as ConstraintLayout)
        var isPinned = false
        if((view.findViewById<ImageView>(R.id.pinnedIcon).background as ColorDrawable).color == Color.GRAY)
            isPinned = true
        var todoName = view.findViewById<EditText>(R.id.newTitle).text.toString()

        //var todoId = db.todosDao().addTodo(TodoItemEntity(itemName = todoName, pinned = isPinned))
        var uncheckedLL = view.findViewById<LinearLayout>(R.id.notdone)
        var checkedLL = view.findViewById<LinearLayout>(R.id.done)
        var uncheckedList = ArrayList<String>()
        var checkedList = ArrayList<String>()
        for(i in 1..uncheckedLL.childCount){
            var task = ((uncheckedLL[i-1] as LinearLayout).get(1) as EditText).text.toString()
            uncheckedList.add(task)
        }
        for(i in 1..checkedLL.childCount){
            var task = ((checkedLL[i-1] as LinearLayout).get(1) as EditText).text.toString()
            checkedList.add(task)
        }
        var params = AsyncTaskParams(todoName, isPinned, checkedList, uncheckedList)
        interactor.insertTasksToDatabase(params)
    }

    @SuppressLint("ResourceType")
    override fun getTodoTasks(todoid : String){
        interactor.getTodoFromDatabase(todoid)
    }

    fun deleteTodo(todo : String){
        interactor.deleteTodo(todo)
    }

    fun updateView(){
        view?.updateViewByExistingTodo(todoItemEntity, tasksList)
    }

}
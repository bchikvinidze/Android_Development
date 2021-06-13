package com.nchikvinidze.todo.second

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.nchikvinidze.todo.R
import com.nchikvinidze.todo.data.TodoDatabase
import com.nchikvinidze.todo.data.entity.TaskItemEntity
import com.nchikvinidze.todo.data.entity.TodoItemEntity

class NewTodoActivity : AppCompatActivity() {
    lateinit var backbutton : ImageButton
    lateinit var todoNameEditText : EditText
    lateinit var newItemAddButton : TextView
    lateinit var uncheckedLL: LinearLayout
    lateinit var checkedLL: LinearLayout
    lateinit var presenter : SecondPresenter
    lateinit var pin : ImageView
    var todoid: Int = -1

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        todoid = intent.getIntExtra("TODOID", -1)

        backbutton = findViewById(R.id.backbutton)
        todoNameEditText = findViewById(R.id.newTitle)
        newItemAddButton = findViewById(R.id.newItemAdd)
        uncheckedLL = findViewById(R.id.notdone)
        checkedLL = findViewById(R.id.done)
        presenter = SecondPresenter(this, TodoDatabase.getInstance(this))
        pin = findViewById(R.id.pinnedIcon)

        backbutton.setOnClickListener {
            onBackPressed()
        }

        pin.setOnClickListener {
            if((pin.background as ColorDrawable).color == Color.GRAY){
                pin.setBackgroundColor(Color.WHITE)
            } else {
                pin.setBackgroundColor(Color.GRAY)
            }
        }

        newItemAddButton.setOnClickListener {
            var newElem = LinearLayout(this)
            var checkBox = CheckBox(this)
            var editText = EditText(this)
            var deleteButton = Button(this)
            checkBox.id = 0
            editText.id = 1
            editText.requestFocus()

            deleteButton.id = 2
            deleteButton.setText("X")
            deleteButton.setBackgroundColor(Color.TRANSPARENT)
            newElem.orientation = LinearLayout.HORIZONTAL
            newElem.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            newElem.addView(checkBox)
            newElem.addView(editText)
            newElem.addView(deleteButton)
            uncheckedLL.addView(newElem)
            deleteButton.setOnClickListener(){
                uncheckedLL.removeView(newElem)
            }
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                var toMove = buttonView.parent as LinearLayout
                if(isChecked) {
                    //toMove.get(0).isEnabled = false
                    toMove.get(1).isEnabled = false
                    toMove.get(2).visibility = View.GONE
                    uncheckedLL.removeView(toMove)
                    checkedLL.addView(toMove)
                } else {
                    checkedLL.removeView(toMove)
                    uncheckedLL.addView(toMove)
                    toMove.get(1).isEnabled = true
                    toMove.get(2).visibility = View.VISIBLE
                }
            }
        }

        if(todoid != -1){
            presenter.getTodoTasks(todoid.toString())
        }
    }

    @SuppressLint("ResourceType")
    fun updateViewByExistingTodo(todoItemEntity : TodoItemEntity, tasksList : ArrayList<TaskItemEntity>){
        var view = findViewById<ConstraintLayout>(R.id.secondPage)
        var pinned = todoItemEntity.pinned
        if(pinned){
            view.findViewById<ImageView>(R.id.pinnedIcon).setBackgroundColor(Color.GRAY)
        } else {
            view.findViewById<ImageView>(R.id.pinnedIcon).setBackgroundColor(Color.WHITE)
        }
        view.findViewById<EditText>(R.id.newTitle).setText(todoItemEntity.itemName)

        for(task in tasksList){
            // aq unda shevqmna is checkboxebi ro mere addView-ti chavamato
            var newElem = LinearLayout(this)
            var checkBox = CheckBox(this)
            var editText = EditText(this)
            var deleteButton = Button(this)
            checkBox.id = 0
            editText.id = 1
            editText.setText(task.value)
            editText.requestFocus()
            deleteButton.id = 2
            deleteButton.setText("X")
            deleteButton.setBackgroundColor(Color.TRANSPARENT)
            newElem.orientation = LinearLayout.HORIZONTAL
            newElem.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            newElem.addView(checkBox)
            newElem.addView(editText)
            newElem.addView(deleteButton)
            if(task.checked){
                //checkBox.isEnabled = false
                checkBox.isChecked = true
                deleteButton.visibility = View.GONE
                editText.isEnabled = false
                checkedLL.addView(newElem)
            } else {
                uncheckedLL.addView(newElem)
                deleteButton.setOnClickListener(){
                    uncheckedLL.removeView(newElem)
                }
            }
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                var toMove = buttonView.parent as LinearLayout
                if(isChecked) {
                    toMove.get(0).isEnabled = false
                    toMove.get(1).isEnabled = false
                    toMove.get(2).visibility = View.GONE
                    uncheckedLL.removeView(toMove)
                    checkedLL.addView(toMove)
                }else {
                    checkedLL.removeView(toMove)
                    uncheckedLL.addView(toMove)
                    toMove.get(1).isEnabled = true
                    toMove.get(2).visibility = View.VISIBLE
                    toMove.get(2).setOnClickListener(){
                        uncheckedLL.removeView(newElem)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(todoid == -1) {
            presenter.onTodoAdded(findViewById(R.id.secondPage))
        } else {
            presenter.deleteTodo(todoid.toString())
            presenter.onTodoAdded(findViewById(R.id.secondPage))
        }
    }
}
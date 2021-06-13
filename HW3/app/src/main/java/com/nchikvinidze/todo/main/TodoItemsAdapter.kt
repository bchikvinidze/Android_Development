package com.nchikvinidze.todo.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.nchikvinidze.todo.R
import com.nchikvinidze.todo.data.entity.TaskItemEntity
import com.nchikvinidze.todo.data.entity.TodoItemEntity
import com.nchikvinidze.todo.second.NewTodoActivity

class TodoItemsAdapter(var presenter: MainPresenter) : RecyclerView.Adapter<TodoItemViewHolder>()  {

    lateinit var passedContext: Context
    var list = ArrayList<Pair<TodoItemEntity, List<TaskItemEntity> > >()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.bindTodo(list[position].first, list[position].second)
        holder.addOnClickListener(passedContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        passedContext = parent.context
        return TodoItemViewHolder(view)
    }
}

class TodoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { // es list_itemtanaa dakavshirebuli da rac chans pirvel gverdze
    var todoName = itemView.findViewById<TextView>(R.id.itemTitle)
    var checkbox1 = itemView.findViewById<TextView>(R.id.checkbox1)
    var checkbox2 = itemView.findViewById<TextView>(R.id.checkbox2)
    var checkbox3 = itemView.findViewById<TextView>(R.id.checkbox3)
    var todoBox = itemView.findViewById<ConstraintLayout>(R.id.list_item_box)
    var todoid = itemView.findViewById<TextView>(R.id.todoIdHolder)

    fun addOnClickListener(context : Context){
        todoBox.setOnClickListener {
            var intent = Intent(context, NewTodoActivity::class.java)
            var idToPass =  todoid.text.toString().toInt()
            intent.putExtra("TODOID", idToPass)
            context.startActivity(intent)
        }
    }

    fun bindTodo(todo : TodoItemEntity, tasks : List<TaskItemEntity>){
        var checked = 0
        var displayCnt = 0
        todoName.setText(todo.itemName)
        todoid.setText(todo.id.toString())
        for(item in tasks){ //taskebi unda gavuchino but HOWW... DAO-s rogor mivwvdeee
            if(item.checked){
                checked += 1
            } else {
                if(displayCnt == 0){
                    checkbox1.setText(item.value)
                    checkbox1.visibility = View.VISIBLE
                    checkbox1.isEnabled = false

                } else if(displayCnt == 1){
                    checkbox2.setText(item.value)
                    checkbox2.visibility = View.VISIBLE
                    checkbox2.isEnabled = false
                } else if(displayCnt == 2){
                    checkbox3.setText(item.value)
                    checkbox3.visibility = View.VISIBLE
                    checkbox3.isEnabled = false
                } else {
                    //todo: samze meti roa raxdeba? pirveli gverdis mxriv arc araferi.
                }
                displayCnt++;
            }
        }
        if(checked > 0){
            var checkedCntDiapleyer = itemView.findViewById<TextView>(R.id.checkedboxes)
            checkedCntDiapleyer.setText("+ " + checked.toString() + " checked items")
            checkedCntDiapleyer.visibility = View.VISIBLE
        }
        if(tasks.size > 3){
            var threeDots = itemView.findViewById<TextView>(R.id.moreuncheckedboxes)
            threeDots.visibility = View.VISIBLE
        }
    }
}
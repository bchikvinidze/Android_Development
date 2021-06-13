package com.nchikvinidze.todo.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nchikvinidze.todo.R
import com.nchikvinidze.todo.data.TodoDatabase
import com.nchikvinidze.todo.data.entity.TaskItemEntity
import com.nchikvinidze.todo.data.entity.TodoItemEntity
import com.nchikvinidze.todo.second.NewTodoActivity

class MainActivity : AppCompatActivity(), IMainView {

    lateinit var otherItemsrv: RecyclerView
    lateinit var pinnedItemsrv: RecyclerView
    lateinit var otherItemsAdapter: TodoItemsAdapter
    lateinit var pinnedItemsAdapter: TodoItemsAdapter
    lateinit var presenter: MainPresenter
    lateinit var db: TodoDatabase
    lateinit var addButton: FloatingActionButton
    lateinit var othersText : TextView
    lateinit var pinnedText : TextView
    lateinit var searchBar : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addButton = findViewById(R.id.addButton)

        db = TodoDatabase.getInstance(this)

        otherItemsrv = findViewById(R.id.othersrv)
        pinnedItemsrv = findViewById(R.id.pinnedrv)
        othersText = findViewById(R.id.others)
        pinnedText =  findViewById(R.id.pinned)
        searchBar = findViewById(R.id.searchEdittext)
        presenter = MainPresenter(this)
        otherItemsAdapter = TodoItemsAdapter(presenter)
        pinnedItemsAdapter = TodoItemsAdapter(presenter)
        otherItemsrv.adapter = otherItemsAdapter
        pinnedItemsrv.adapter = pinnedItemsAdapter
        otherItemsrv.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        pinnedItemsrv.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        addButton.setOnClickListener {
            var intent = Intent(this, NewTodoActivity::class.java)
            intent.putExtra("TODOID", -1)
            startActivity(intent)
        }
        presenter.getTodos()

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var text = searchBar.text.toString()
                presenter.getFilteredTodos(text)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        otherItemsAdapter = TodoItemsAdapter(presenter)
        pinnedItemsAdapter = TodoItemsAdapter(presenter)
        otherItemsrv.adapter = otherItemsAdapter
        pinnedItemsrv.adapter = pinnedItemsAdapter
        otherItemsrv.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        pinnedItemsrv.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        presenter.getTodos()
        pinnedItemsAdapter.notifyDataSetChanged()
        otherItemsAdapter.notifyDataSetChanged()
    }

    override fun displayTodos(todos: ArrayList<Pair<TodoItemEntity, List<TaskItemEntity> > >) {
        pinnedItemsAdapter.list = ArrayList<Pair<TodoItemEntity, List<TaskItemEntity> > >()
        otherItemsAdapter.list = ArrayList<Pair<TodoItemEntity, List<TaskItemEntity> > >()
        pinnedText.visibility = View.GONE
        othersText.visibility = View.GONE
        for(todo in todos){
            if(todo.first.pinned){
                pinnedItemsAdapter.list.add(todo)
                pinnedText.visibility = View.VISIBLE
                pinnedItemsAdapter.notifyDataSetChanged()
            } else {
                otherItemsAdapter.list.add(todo)
                othersText.visibility = View.VISIBLE
                otherItemsAdapter.notifyDataSetChanged()
            }
        }
    }
}

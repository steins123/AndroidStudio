package com.example.tutorialproject

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(private val todos: MutableList<Todo>
):RecyclerView.Adapter<TodoAdapter.TodoViewHolder>()
{
    //Viewholder
    class TodoViewHolder(itemView:View ):RecyclerView.ViewHolder(itemView)

    //This function creates the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
       return TodoViewHolder(LayoutInflater.from(parent.context).inflate(
           R.layout.item_todo,
           parent,
           false
       ))
    }

    fun addTodo(todo : Todo)
    {
        todos.add(todo)
        notifyItemInserted(todos.size-1)
    }

    fun deleteTodo()
    {
        //remove if boolean is checked
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    //binds the data from todo List to the View of the list
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodo = todos[position];
        holder.itemView.apply {
            tvTodoTitle.text = currentTodo.title
            cbDone.isChecked = currentTodo.isChecked
            toggleStrikethrough(tvTodoTitle, currentTodo.isChecked)
            //wird gechalled wenn checkbox state == changed
            cbDone.setOnCheckedChangeListener { _ , isChecked ->
                toggleStrikethrough(tvTodoTitle, isChecked)
            currentTodo.isChecked = !currentTodo.isChecked
            }
        }
    }

    private fun toggleStrikethrough(tvTodoTitle: TextView, isChecked:Boolean)
    {
        if(isChecked)
        {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }
        else
        {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    //return amount of items in the list
    override fun getItemCount(): Int {
        return todos.size
    }
}
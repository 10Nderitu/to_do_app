package com.example.kotlintodo

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItem: TextView = itemView.findViewById(R.id.tvItem)
        val cbDone: CheckBox = itemView.findViewById(R.id.cbDone)
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        todos.removeAll { todo -> todo.isChecked }
        notifyDataSetChanged()
    }
    private fun toggleStrikethrough(tvTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvTitle.paintFlags = tvTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTitle.paintFlags = tvTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.tvItem.text = curTodo.title
        holder.cbDone.isChecked = curTodo.isChecked
        toggleStrikethrough(holder.tvItem, curTodo.isChecked)
        holder.cbDone.setOnCheckedChangeListener { _, isChecked ->
            toggleStrikethrough(holder.tvItem, isChecked)
            curTodo.isChecked = !curTodo.isChecked
        }
    }
//item count

    override fun getItemCount(): Int {
        return todos.size
    }
}

package br.com.fiap.lista_tarefa_cp5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(private var tasks: MutableList<Task>) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.text_task_title)
        val completedCheckBox: CheckBox = itemView.findViewById(R.id.checkbox_task_completed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.titleTextView.text = currentTask.title
        holder.completedCheckBox.isChecked = currentTask.completed

        holder.completedCheckBox.setOnClickListener {
        }

        holder.itemView.findViewById<Button>(R.id.button_delete_task).setOnClickListener {
            tasks.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = tasks.size
}

package br.com.fiap.lista_tarefa_cp5

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fiap.lista_tarefa_cp5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var tasksList: MutableList<Task>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tasksList = mutableListOf()
        tasksAdapter = TasksAdapter(tasksList)

        binding.recyclerViewTasks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = tasksAdapter
        }

        // Limpa todas as tarefas
        binding.fabClearTasks.setOnClickListener {
            clearAllTasks()
        }

        // Adiciona nova tarefa
        binding.fabAddTask.setOnClickListener {
            val newTaskText = binding.editTextNewTask.text.toString().trim()
            if (newTaskText.isNotEmpty()) {
                addTask(Task(newTaskText, false))
                binding.editTextNewTask.text.clear()
            } else {
                Toast.makeText(this, "Digite o texto da nova tarefa", Toast.LENGTH_SHORT).show()
            }
        }

        // Adiciona nova tarefa apertando o enter do teclado do celular
        binding.editTextNewTask.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val newTaskText = binding.editTextNewTask.text.toString().trim()
                if (newTaskText.isNotEmpty()) {
                    addTask(Task(newTaskText, false))
                    binding.editTextNewTask.text.clear()
                } else {
                    Toast.makeText(this, "Digite o texto da nova tarefa", Toast.LENGTH_SHORT).show()
                }
                true
            } else {
                false
            }
        }

        sharedPreferences = getSharedPreferences("TasksPref", Context.MODE_PRIVATE)

        loadTasksFromSharedPreferences()
    }

    // Adiciona a tarefa
    private fun addTask(task: Task) {
        tasksList.add(task)
        saveTasksToSharedPreferences()
        tasksAdapter.notifyDataSetChanged()
    }

    // Salva as tarefas
    private fun saveTasksToSharedPreferences() {
        val editor = sharedPreferences.edit()
        editor.putString("tasks", Task.toJsonArray(tasksList).toString())
        editor.apply()
    }

    // Carrega as tarefas salvas
    private fun loadTasksFromSharedPreferences() {
        val tasksJson = sharedPreferences.getString("tasks", null)
        if (!tasksJson.isNullOrBlank()) {
            tasksList.clear()
            tasksList.addAll(Task.fromJsonArray(tasksJson))
            tasksAdapter.notifyDataSetChanged()
        }
    }

    // Limpa lista de tarefas
    private fun clearAllTasks() {
        val editor = sharedPreferences.edit()
        editor.clear().apply()
        tasksList.clear()
        tasksAdapter.notifyDataSetChanged()
    }

}


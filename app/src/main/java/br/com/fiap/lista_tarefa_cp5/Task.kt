package br.com.fiap.lista_tarefa_cp5

import org.json.JSONArray
import org.json.JSONObject

data class Task(val title: String, val completed: Boolean) {
    companion object {
        fun fromJsonArray(json: String): List<Task> {
            val tasks = mutableListOf<Task>()
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                tasks.add(Task(jsonObject.getString("title"), jsonObject.getBoolean("completed")))
            }
            return tasks
        }

        fun toJsonArray(tasks: List<Task>): JSONArray {
            val jsonArray = JSONArray()
            for (task in tasks) {
                val jsonObject = JSONObject()
                jsonObject.put("title", task.title)
                jsonObject.put("completed", task.completed)
                jsonArray.put(jsonObject)
            }
            return jsonArray
        }
    }
}

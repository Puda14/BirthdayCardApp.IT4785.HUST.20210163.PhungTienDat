package com.example.studentmanagerfragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagerfragment.AddStudentFragment
import com.example.studentmanagerfragment.EditStudentFragment

class MainActivity : AppCompatActivity() {

    private lateinit var studentListView: ListView
    val students = mutableListOf(
        "Nguyen Van A - 20210163",
        "Nguyen Van B - 20210163"
    )
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentListView = findViewById(R.id.list_view)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students)
        studentListView.adapter = adapter

        studentListView.setOnItemClickListener { _, _, position, _ ->
            val student = students[position]
            Log.d("MainActivity", "Editing student: $student")
            // Navigate to EditFragment
            val fragment = EditStudentFragment()
            val bundle = Bundle()
            bundle.putString("student_name", student)
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        registerForContextMenu(studentListView)
    }

    override fun onCreateContextMenu(
        menu: android.view.ContextMenu?,
        v: View?,
        menuInfo: android.view.ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val student = students[info.position]

        when (item.itemId) {
            R.id.action_edit -> {
                Log.d("MainActivity", "Editing student: $student")
                val fragment = EditStudentFragment()
                val bundle = Bundle()
                bundle.putString("student_name", student)
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
                return true
            }
            R.id.action_delete -> {
                Log.d("MainActivity", "Deleting student: $student")
                students.removeAt(info.position)
                Toast.makeText(this, "Student removed", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged() // Notify adapter of data change
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_new -> {
                Log.d("MainActivity", "Adding new student")
                val fragment = AddStudentFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Method to update list when student is added or edited
    fun updateStudentList(updatedStudents: List<String>) {
        students.clear()
        students.addAll(updatedStudents)
        adapter.notifyDataSetChanged()
        Log.d("MainActivity", "Student list updated: $students")
    }
}

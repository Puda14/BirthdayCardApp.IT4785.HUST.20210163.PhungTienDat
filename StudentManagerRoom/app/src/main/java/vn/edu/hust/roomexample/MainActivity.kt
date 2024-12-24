package vn.edu.hust.roomexample

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.edu.hust.roomexample.databinding.ActivityMainBinding
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.core.widget.doOnTextChanged

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var studentAdapter: StudentAdapter
  private lateinit var studentDao: StudentDao

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    studentDao = StudentDatabase.getInstance(this).studentDao()

    setupRecyclerView()
    loadStudents()

    binding.buttonDeleteSelected.setOnClickListener { deleteSelectedStudents() }

    binding.editSearch.doOnTextChanged { text, _, _, _ ->
      filterStudents(text.toString())
    }
  }

  private fun setupRecyclerView() {
    studentAdapter = StudentAdapter(
      students = emptyList(),
      onStudentClick = { openStudentDetail(it) },
      onSelectionChanged = { updateDeleteButtonState(it) }
    )

    binding.recyclerView.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = studentAdapter
    }
  }

  private fun loadStudents() {
    lifecycleScope.launch(Dispatchers.IO) {
      val students = studentDao.getAllStudents()
      runOnUiThread {
        studentAdapter.updateData(students)
      }
    }
  }

  private fun deleteSelectedStudents() {
    val selectedStudents = studentAdapter.getSelectedStudents()
    if (selectedStudents.isNotEmpty()) {
      lifecycleScope.launch(Dispatchers.IO) {
        studentDao.deleteStudents(selectedStudents.map { it._id })
        loadStudents()
      }
    }
  }

  private fun updateDeleteButtonState(selectedStudents: List<Student>) {
    binding.buttonDeleteSelected.isEnabled = selectedStudents.isNotEmpty()
  }

  private fun openStudentDetail(student: Student) {
    val intent = Intent(this, StudentDetailActivity::class.java)
    intent.putExtra("student_id", student._id)
    startActivity(intent)
  }

  private fun filterStudents(query: String?) {
    if (query.isNullOrBlank()) {
      loadStudents()
      return
    }

    lifecycleScope.launch(Dispatchers.IO) {
      val students = studentDao.searchStudents("%$query%", "%$query%")
      runOnUiThread {
        studentAdapter.updateData(students)
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
}

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_add -> {
        val intent = Intent(this, AddStudentActivity::class.java)
        startActivity(intent)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onResume() {
    super.onResume()
    loadStudents()
  }
}

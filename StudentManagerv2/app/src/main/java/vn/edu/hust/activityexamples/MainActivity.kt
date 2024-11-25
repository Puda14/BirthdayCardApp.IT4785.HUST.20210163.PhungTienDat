package vn.edu.hust.activityexamples

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import android.util.Log

class MainActivity : AppCompatActivity() {
  private val studentList = mutableListOf<String>()
  private lateinit var adapter: ArrayAdapter<String>

  private val launcher =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
      if (result.resultCode == RESULT_OK) {
        Log.d("MainActivity", "Received result OK")
        val name = result.data?.getStringExtra("hoten")
        val id = result.data?.getStringExtra("mssv")
        val position = result.data?.getIntExtra("position", -1)

        Log.d("MainActivity", "Received student data: name=$name, id=$id, position=$position")

        if (name != null && id != null && position != null) {
          val student = "$name - $id"
          if (position != -1) {
            studentList[position] = student
            Log.d("MainActivity", "Updated student at position $position: $student")
          } else {
            studentList.add(student)
            Log.d("MainActivity", "Added new student: $student")
          }
          adapter.notifyDataSetChanged()
        } else {
          Log.e("MainActivity", "Invalid student data received: name=$name, id=$id, position=$position")
        }
      } else {
        Log.d("MainActivity", "Activity result was cancelled or failed")
      }
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList)
    val listView = findViewById<ListView>(R.id.listView)
    listView.adapter = adapter
    registerForContextMenu(listView)

    studentList.add("Nguyễn Huy Tuấn - 001")
    studentList.add("Đỗ Thùy Dương - 002")
    studentList.add("Lê Quang Minh - 003")
    studentList.add("Mai Việt Dũng - 004")
    studentList.add("Lê Cao Phong - 005")

    listView.setOnItemClickListener { _, _, position, _ ->
      Log.d("MainActivity", "ListView item clicked at position $position")
      val intent = Intent(this, AddStudentActivity::class.java)
      val studentData = studentList[position].split(" - ")
      intent.putExtra("hoten", studentData[0])
      intent.putExtra("mssv", studentData[1])
      intent.putExtra("position", position)
      launcher.launch(intent)
    }
  }

  override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu)
    Log.d("MainActivity", "Context menu created")
  }

  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterContextMenuInfo
    val position = info.position
    Log.d("MainActivity", "Context item selected at position $position")

    when (item.itemId) {
      R.id.menu_edit -> {
        val intent = Intent(this, AddStudentActivity::class.java)
        val studentData = studentList[position].split(" - ")
        intent.putExtra("hoten", studentData[0])
        intent.putExtra("mssv", studentData[1])
        intent.putExtra("position", position)
        Log.d("MainActivity", "Starting AddStudentActivity to edit student at position $position")
        launcher.launch(intent)
      }
      R.id.menu_remove -> {
        studentList.removeAt(position)
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Student removed", Toast.LENGTH_SHORT).show()
        Log.d("MainActivity", "Removed student at position $position")
      }
    }
    return super.onContextItemSelected(item)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.menu_add) {
      Log.d("MainActivity", "Starting AddStudentActivity to add new student")
      val intent = Intent(this, AddStudentActivity::class.java)
      launcher.launch(intent)
      return true
    }
    return super.onOptionsItemSelected(item)
  }
}


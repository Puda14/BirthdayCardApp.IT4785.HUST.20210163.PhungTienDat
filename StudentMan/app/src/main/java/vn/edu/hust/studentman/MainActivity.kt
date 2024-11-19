package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private val students = mutableListOf(
    StudentModel("Nguyễn Văn Bình", "20210001"),
    StudentModel("Trần Thị Cẩm", "20210002"),
    StudentModel("Lê Hoàng Duy", "20210003"),
    StudentModel("Phạm Thị Hà", "20210004"),
    StudentModel("Đỗ Minh Hiếu", "20210005"),
    StudentModel("Vũ Thị Khánh", "20210006"),
    StudentModel("Hoàng Văn Khoa", "20210007"),
    StudentModel("Bùi Thị Liên", "20210008"),
    StudentModel("Đinh Văn Mạnh", "20210009"),
    StudentModel("Nguyễn Thị Nga", "20210010"),
    StudentModel("Phạm Văn Phát", "20210011"),
    StudentModel("Trần Thị Quyên", "20210012"),
    StudentModel("Lê Thị Sương", "20210013"),
    StudentModel("Vũ Văn Tâm", "20210014"),
    StudentModel("Hoàng Thị Thanh", "20210015"),
    StudentModel("Đỗ Văn Thắng", "20210016"),
    StudentModel("Nguyễn Thị Uyên", "20210017"),
    StudentModel("Trần Văn Việt", "20210018"),
    StudentModel("Phạm Thị Xuân", "20210019"),
    StudentModel("Lê Văn Yến", "20210020")
  )

  private val studentAdapter = StudentAdapter(students, this::editStudent, this::deleteStudent)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    findViewById<RecyclerView>(R.id.recycler_view_students).run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddStudentDialog()
    }
  }

  private fun showAddStudentDialog() {
    val dialogView = LayoutInflater.from(this@MainActivity)
      .inflate(R.layout.layout_alert_dialog, null)

    val editHoten = dialogView.findViewById<EditText>(R.id.edit_hoten)
    val editMssv = dialogView.findViewById<EditText>(R.id.edit_mssv)

    AlertDialog.Builder(this)
      .setTitle("Nhap thong tin sinh vien")
      .setView(dialogView)
      .setPositiveButton("OK") { _, _ ->
        val hoten = editHoten.text.toString()
        val mssv = editMssv.text.toString()
        if (hoten.isNotEmpty() && mssv.isNotEmpty()) {
          addStudent(StudentModel(hoten, mssv))
        }
      }
      .setNegativeButton("Cancel", null)
      .create().show()
  }

  private fun addStudent(student: StudentModel) {
    students.add(student)
    studentAdapter.notifyItemInserted(students.size - 1)
  }

  private fun editStudent(position: Int) {
    val student = students[position]

    val dialogView = LayoutInflater.from(this@MainActivity).inflate(R.layout.layout_alert_dialog, null)

    val editHoten = dialogView.findViewById<EditText>(R.id.edit_hoten)
    val editMssv = dialogView.findViewById<EditText>(R.id.edit_mssv)

    editHoten.setText(student.studentName)
    editMssv.setText(student.studentId)

    AlertDialog.Builder(this)
      .setTitle("Nhap thong tin sinh vien")
      .setView(dialogView)
      .setPositiveButton("OK") { _, _ ->
        val newHoten = editHoten.text.toString()
        val newMssv = editMssv.text.toString()
        if (newHoten.isNotEmpty() && newMssv.isNotEmpty()) {
          students[position] = StudentModel(newHoten, newMssv)
          studentAdapter.notifyItemChanged(position)
        }
      }
      .setNegativeButton("Cancel", null)
      .create()
      .show()
  }

  private fun deleteStudent(position: Int) {
    val deletedStudent = students[position]
    AlertDialog.Builder(this)
      .setTitle("Xóa sinh viên")
      .setMessage("Bạn có chắc muốn xóa sinh viên này?")
      .setPositiveButton("Delete") { _, _ ->
        students.removeAt(position)
        studentAdapter.notifyItemRemoved(position)

        Snackbar.make(findViewById(R.id.recycler_view_students),
          "Đã xóa sinh viên: ${deletedStudent.studentName}",
          Snackbar.LENGTH_LONG)
          .setAction("Undo") {
            students.add(position, deletedStudent)
            studentAdapter.notifyItemInserted(position)
          }
          .show()
      }
      .setNegativeButton("Cancel", null)
      .create()
      .show()
  }
}
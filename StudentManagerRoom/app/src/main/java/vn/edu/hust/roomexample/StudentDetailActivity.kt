package vn.edu.hust.roomexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.edu.hust.roomexample.databinding.ActivityStudentDetailBinding

class StudentDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentDetailBinding
    private var studentId: Int = -1
    private lateinit var studentDao: StudentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentDao = StudentDatabase.getInstance(this).studentDao()
        studentId = intent.getIntExtra("student_id", -1)

        if (studentId != -1) {
            loadStudentDetails()
        } else {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.buttonUpdate.setOnClickListener { updateStudent() }
        binding.buttonDelete.setOnClickListener { deleteStudent() }
    }

    private fun loadStudentDetails() {
        lifecycleScope.launch(Dispatchers.IO) {
            val student = studentDao.findStudentById(studentId).firstOrNull()
            student?.let {
                runOnUiThread {
                    binding.editHoten.setText(it.hoten)
                    binding.editMssv.setText(it.mssv)
                    binding.editNgaysinh.setText(it.ngaysinh)
                    binding.editEmail.setText(it.email)
                }
            }
        }
    }

    private fun updateStudent() {
        val newHoten = binding.editHoten.text.toString()
        val newMssv = binding.editMssv.text.toString()
        val newNgaysinh = binding.editNgaysinh.text.toString()
        val newEmail = binding.editEmail.text.toString()

        if (newHoten.isBlank() || newMssv.isBlank() || newNgaysinh.isBlank() || newEmail.isBlank()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val student = Student(studentId, newHoten, newMssv, newNgaysinh, newEmail)
            studentDao.updateStudent(student)
            runOnUiThread {
                Toast.makeText(this@StudentDetailActivity, "Student updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun deleteStudent() {
        lifecycleScope.launch(Dispatchers.IO) {
            studentDao.deleteStudentById(studentId)
            runOnUiThread {
                Toast.makeText(this@StudentDetailActivity, "Student deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

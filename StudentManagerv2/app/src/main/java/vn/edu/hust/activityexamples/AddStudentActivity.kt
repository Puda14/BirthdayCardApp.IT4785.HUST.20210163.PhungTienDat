package vn.edu.hust.activityexamples

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

import android.util.Log

class AddStudentActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_student)

    val editHoten = findViewById<EditText>(R.id.edit_hoten)
    val editMssv = findViewById<EditText>(R.id.edit_mssv)
    val position = intent.getIntExtra("position", -1)

    Log.d("AddStudentActivity", "Received position: $position")
    Log.d("AddStudentActivity", "Received: ${intent.getStringExtra("hoten")} - ${intent.getStringExtra("mssv")} - ${position}")

    // Display student info if editing
    if (position != -1) {
      // Directly use the passed data, no need to split
      val hoten = intent.getStringExtra("hoten")
      val mssv = intent.getStringExtra("mssv")
      Log.d("AddStudentActivity", "Editing student: $hoten - $mssv")
      editHoten.setText(hoten)
      editMssv.setText(mssv)
    }

    findViewById<Button>(R.id.button_ok).setOnClickListener {
      val resultIntent = intent
      resultIntent.putExtra("hoten", editHoten.text.toString())
      resultIntent.putExtra("mssv", editMssv.text.toString())
      resultIntent.putExtra("position", position) // Pass position back
      Log.d("AddStudentActivity", "Returning data: hoten=${editHoten.text}, mssv=${editMssv.text}, position=$position")
      setResult(Activity.RESULT_OK, resultIntent)
      finish()
    }

    findViewById<Button>(R.id.button_cancel).setOnClickListener {
      Log.d("AddStudentActivity", "Cancellation requested, finishing activity")
      setResult(Activity.RESULT_CANCELED)
      finish()
    }
  }
}



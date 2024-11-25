package vn.edu.hust.activityexamples

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_second)

    // TODO: Lay du lieu dinh kem khi mo activity
    val value1 = intent.getIntExtra("param3", 0)
    val value2 = intent.getDoubleExtra("param2", 0.0)
    val value3 = intent.getStringExtra("param1")

    val textContent = findViewById<TextView>(R.id.text_content)
    textContent.text = "$value1 - $value2 - $value3"

  }
}
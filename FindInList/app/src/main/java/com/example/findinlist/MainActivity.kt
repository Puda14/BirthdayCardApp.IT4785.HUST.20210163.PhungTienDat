package com.example.findinlist
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findinlist.adapter.StudentAdapter
import com.example.findinlist.model.Student
class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentList: List<Student>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val etSearch = findViewById<EditText>(R.id.etSearch)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Tạo danh sách sinh viên mẫu
        studentList = listOf(
            Student("Nguyen Anh Binh", "20220001"),
            Student("Tran Thi Cam", "20220002"),
            Student("Le Van Dat", "20220003"),
            Student("Pham Minh Duy", "20220004"),
            Student("Do Hong Giau", "20220005"),
            Student("Ngo Thi Hoa", "20220006"),
            Student("Ly Thanh Hieu", "20220007"),
            Student("Phan Van Khoa", "20220008"),
            Student("Nguyen Thi Lan", "20220009"),
            Student("Tran Minh Long", "20220010"),
            Student("Le Thi My", "20220011"),
            Student("Pham Duc Nam", "20220012"),
            Student("Do Van Phat", "20220013"),
            Student("Ngo Hong Phuc", "20220014"),
            Student("Ly Minh Quoc", "20220015"),
            Student("Phan Bao Quyen", "20220016"),
            Student("Nguyen Duc Sang", "20220017"),
            Student("Tran Hong Son", "20220018"),
            Student("Le Minh Tam", "20220019"),
            Student("Pham Thi Thao", "20220020"),
            Student("Do Thi Trang", "20220021"),
            Student("Ngo Quoc Truong", "20220022"),
            Student("Ly Anh Tuan", "20220023"),
            Student("Phan Minh Uyen", "20220024"),
            Student("Nguyen Thi Van", "20220025"),
            Student("Tran Minh Vu", "20220026"),
            Student("Le Hoang Xuan", "20220027"),
            Student("Pham Bao Yen", "20220028"),
            Student("Do Quoc Bao", "20220029"),
            Student("Ngo Thi Tam", "20220030")
        )
        // Thiết lập RecyclerView với danh sách sinh viên
        studentAdapter = StudentAdapter(studentList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = studentAdapter
        // Thiết lập tìm kiếm khi người dùng nhập từ khóa
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val keyword = s.toString().trim()
                if (keyword.length > 2) {
                    filterList(keyword)
                } else {
                    studentAdapter.updateList(studentList)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
    // Hàm lọc danh sách sinh viên dựa trên từ khóa
    private fun filterList(keyword: String) {
        val filteredList = studentList.filter {
            it.name.contains(keyword, ignoreCase = true) || it.mssv.contains(keyword)
        }
        studentAdapter.updateList(filteredList)
    }
}
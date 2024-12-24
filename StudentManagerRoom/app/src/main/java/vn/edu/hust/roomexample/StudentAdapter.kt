package vn.edu.hust.roomexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private var students: List<Student>,
    private val onStudentClick: (Student) -> Unit,
    private val onSelectionChanged: (List<Student>) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private val selectedStudents = mutableSetOf<Student>()

    inner class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMssv: TextView = view.findViewById(R.id.tv_mssv)
        val tvHoten: TextView = view.findViewById(R.id.tv_hoten)
        val tvNgaysinh: TextView = view.findViewById(R.id.tv_ngaysinh)
        val tvEmail: TextView = view.findViewById(R.id.tv_email)
        val cbSelect: CheckBox = view.findViewById(R.id.cb_select)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.tvMssv.text = student.mssv
        holder.tvHoten.text = student.hoten
        holder.tvNgaysinh.text = student.ngaysinh
        holder.tvEmail.text = student.email

        holder.cbSelect.setOnCheckedChangeListener(null)
        holder.cbSelect.isChecked = selectedStudents.contains(student)

        holder.cbSelect.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) selectedStudents.add(student) else selectedStudents.remove(student)
            onSelectionChanged(selectedStudents.toList())
        }

        holder.itemView.setOnClickListener { onStudentClick(student) }
    }

    override fun getItemCount() = students.size

    fun updateData(newStudents: List<Student>) {
        this.students = newStudents
        notifyDataSetChanged()
    }

    fun getSelectedStudents(): List<Student> {
        return selectedStudents.toList()
    }
}

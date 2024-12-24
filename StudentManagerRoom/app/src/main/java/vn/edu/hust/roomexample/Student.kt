package vn.edu.hust.roomexample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
  @PrimaryKey(autoGenerate = true)
  val _id: Int = 0,
  val hoten: String,
  val mssv: String,
  val ngaysinh: String,
  val email: String
)

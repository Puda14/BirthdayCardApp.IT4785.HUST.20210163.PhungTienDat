package vn.edu.hust.roomexample

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {
  @Query("select * from students")
  fun getAllStudents(): List<Student>

  @Query("SELECT * FROM students WHERE _id = :id")
  fun findStudentById(id: Int): List<Student>

  @Query("select * from students where hoten like :name")
  fun findStudentByName(name: String): List<Student>

  @Query("SELECT * FROM students WHERE mssv LIKE :mssv OR hoten LIKE :name")
  fun searchStudents(mssv: String, name: String): List<Student>

  @Insert
  fun insertStudent(student: Student): Long

  @Query("DELETE FROM students WHERE _id IN (:ids)")
  fun deleteStudents(ids: List<Int>)

  @Query("DELETE FROM students WHERE _id = :id")
  fun deleteStudentById(id: Int)

  @Update
  fun updateStudent(student: Student)
}
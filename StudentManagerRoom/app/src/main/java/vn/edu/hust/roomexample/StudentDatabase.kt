package vn.edu.hust.roomexample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase: RoomDatabase() {
  abstract fun studentDao(): StudentDao
  companion object {
    @Volatile
    private var INSTANCE: StudentDatabase? = null
    fun getInstance(context: Context): StudentDatabase {
      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: Room.databaseBuilder(
          context.applicationContext,
          StudentDatabase::class.java,
          "student_db"
        )
          .fallbackToDestructiveMigration()
          .build()
          .also { INSTANCE = it }
      }

    }
  }
}
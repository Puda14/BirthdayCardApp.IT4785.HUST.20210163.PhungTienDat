package com.example.gmail
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        // Create sample email data
        val emailList = listOf(
            Email("Phung Tien Dat", "20210163", "Reminder: Submit assignment before 23:59PM 29 Oct, 2024", "16:08 PM"),
            Email("Alice Johnson", "Project Update", "The project is on track and will be completed by next week.", "14:10 PM"),
            Email("Bob Brown", "Invoice #4567", "Please find attached the invoice for last month's services.", "13:02 PM"),
            Email("YouTube", "Trending Videos", "Catch up on the latest trending videos today!", "11:59 AM"),
            Email("Spotify", "Weekly Playlist", "Your new weekly playlist is ready to listen.", "10:20 AM"),
            Email("HR Department", "Policy Update", "Please review the latest updates to the company policies.", "9:15 AM"),
            Email("Gym Center", "Membership Renewal", "Your membership is expiring soon. Renew today for a discount.", "Yesterday")
        )
        val adapter = EmailAdapter(emailList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        // Floating Action Button click action
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            Toast.makeText(this, "Compose Email", Toast.LENGTH_SHORT).show()
        }
    }
}
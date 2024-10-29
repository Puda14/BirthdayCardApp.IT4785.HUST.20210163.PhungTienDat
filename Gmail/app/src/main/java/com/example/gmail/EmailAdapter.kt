package com.example.gmail
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class EmailAdapter(private val emailList: List<Email>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {
    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val emailSender: TextView = itemView.findViewById(R.id.emailSender)
        val emailSubject: TextView = itemView.findViewById(R.id.emailSubject)
        val emailSummary: TextView = itemView.findViewById(R.id.emailSummary)
        val emailTime: TextView = itemView.findViewById(R.id.emailTime)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }
    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val email = emailList[position]
        holder.emailSender.text = email.sender
        holder.emailSubject.text = email.subject
        holder.emailSummary.text = email.summary
        holder.emailTime.text = email.time
    }
    override fun getItemCount(): Int {
        return emailList.size
    }
}
package com.example.studentmanagerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class AddStudentFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var idEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_student, container, false)

        nameEditText = view.findViewById(R.id.name_edit_text)
        idEditText = view.findViewById(R.id.id_edit_text)

        val addButton: Button = view.findViewById(R.id.save_button)
        addButton.setOnClickListener {
            val studentName = nameEditText.text.toString()
            val studentId = idEditText.text.toString()
            val student = "$studentName - $studentId"
            if (activity is MainActivity) {
                // Call the function in MainActivity to update the list
                val updatedList = (activity as MainActivity).students.toMutableList()
                updatedList.add(student)
                (activity as MainActivity).updateStudentList(updatedList)
            }
            requireActivity().supportFragmentManager.popBackStack() // Close fragment
        }

        return view
    }
}

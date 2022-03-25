package com.example.teacherhelper.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherhelper.R
import com.example.teacherhelper.model.entities.Student
import com.example.teacherhelper.model.utils.Utilities
import com.example.teacherhelper.viewmodel.adapters.StudentListAdapter
import com.example.teacherhelper.viewmodel.vms.StudentsViewModel

class FragmentStudents: Fragment() {

    private lateinit var viewModel: StudentsViewModel
    private lateinit var studentListAdapter: StudentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[StudentsViewModel::class.java]
        studentListAdapter = StudentListAdapter(viewModel.students)
        return inflater.inflate(R.layout.fragment_students, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.students.observe(viewLifecycleOwner, {studentListAdapter.notifyItemRangeChanged(0, studentListAdapter.itemCount)})

        view.findViewById<Button>(R.id.new_student_button).setOnClickListener{
            val albumNumber = view.findViewById<EditText>(R.id.student_album_number_editText).text.toString()
            val firstName = view.findViewById<EditText>(R.id.student_first_name_editText).text.toString()
            val lastName = view.findViewById<EditText>(R.id.student_last_name_editText).text.toString()
            if(albumNumber.isNotBlank() && firstName.isNotBlank() && lastName.isNotBlank() && Utilities.albumNumberNotInList(albumNumber, viewModel.students)) {
                viewModel.addStudent(Student(albumNumber, firstName, lastName))
                viewModel.students.observe(viewLifecycleOwner, {studentListAdapter.notifyItemInserted(studentListAdapter.itemCount)})
            }
        }

        view.findViewById<RecyclerView>(R.id.students_list).adapter = studentListAdapter

        view.findViewById<Button>(R.id.student_grades_button).setOnClickListener{
            if(studentListAdapter.getSelectedPosition() == -1) {
                val elementNotPickedAlert = "Nie wybrano studenta"
                Toast.makeText(context, elementNotPickedAlert, Toast.LENGTH_SHORT).show()
            }
            else
                it.findNavController().navigate(R.id.action_fragmentStudents_to_fragmentStudentGrades, bundleOf(
                        "LAST_PICKED_ALBUM_NUMBER" to studentListAdapter.getSelectedAlbumNumber(),
                        "LAST_PICKED_STUDENT_NAME" to studentListAdapter.getSelectedStudentName()))
        }
    }
}
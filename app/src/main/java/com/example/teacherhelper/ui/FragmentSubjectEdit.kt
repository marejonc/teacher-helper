package com.example.teacherhelper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherhelper.R
import com.example.teacherhelper.model.entities.StudentSubjectRelation
import com.example.teacherhelper.model.utils.Utilities
import com.example.teacherhelper.viewmodel.adapters.StudentListAdapter
import com.example.teacherhelper.viewmodel.vms.EditSubjectViewModel

class FragmentSubjectEdit: Fragment() {

    private lateinit var viewModel: EditSubjectViewModel
    private lateinit var attendingStudentsListAdapter: StudentListAdapter
    private lateinit var studentsListAdapter: StudentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[EditSubjectViewModel::class.java]
        viewModel.setSubjectId(arguments?.getInt("LAST_PICKED_SUBJECT_ID")!!)
        attendingStudentsListAdapter = StudentListAdapter(viewModel.attendingStudents)
        studentsListAdapter = StudentListAdapter(viewModel.students)
        return inflater.inflate(R.layout.fragment_subject_edit,container,false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.attendingStudents.observe(viewLifecycleOwner, {attendingStudentsListAdapter.notifyDataSetChanged()})
        viewModel.students.observe(viewLifecycleOwner, {studentsListAdapter.notifyItemRangeChanged(0, studentsListAdapter.itemCount)})

        view.findViewById<Button>(R.id.add_student_to_subject_button).setOnClickListener {
            if(studentsListAdapter.getSelectedPosition() == -1) {
                val elementNotPickedAlert = "Nie wybrano studenta"
                Toast.makeText(context, elementNotPickedAlert, Toast.LENGTH_SHORT).show()
            }
            else if(!Utilities.albumNumberNotInList(studentsListAdapter.getSelectedAlbumNumber()!!, viewModel.attendingStudents)) {
                val elementAlreadyInListAlert = "Student już dodany"
                Toast.makeText(context, elementAlreadyInListAlert, Toast.LENGTH_SHORT).show()
            }
            else {
                viewModel.addStudentSubjectRelation(StudentSubjectRelation(0, arguments?.getInt("LAST_PICKED_SUBJECT_ID")!!, studentsListAdapter.getSelectedAlbumNumber()))
                viewModel.attendingStudents.observe(viewLifecycleOwner, {attendingStudentsListAdapter.notifyItemInserted(attendingStudentsListAdapter.itemCount)})
            }
        }

        view.findViewById<Button>(R.id.delete_student_from_subject_button).setOnClickListener {
            if(attendingStudentsListAdapter.getSelectedPosition() == -1) {
                val elementNotPickedAlert = "Nie wybrano studenta"
                Toast.makeText(context, elementNotPickedAlert, Toast.LENGTH_SHORT).show()
            }
            else {
                viewModel.deleteStudentFromSubject(attendingStudentsListAdapter.getSelectedAlbumNumber()!!,
                    arguments?.getInt("LAST_PICKED_SUBJECT_ID")!!)
                viewModel.attendingStudents.observe(viewLifecycleOwner, {attendingStudentsListAdapter.notifyItemRemoved(attendingStudentsListAdapter.getSelectedPosition())})
                attendingStudentsListAdapter.resetSelectedPosition()
            }
        }

        view.findViewById<RecyclerView>(R.id.all_students_list).let {
            it.adapter = studentsListAdapter
            it.itemAnimator = null
        }
        view.findViewById<RecyclerView>(R.id.participants_list).let {
            it.adapter = attendingStudentsListAdapter
            it.itemAnimator = null
        }

        view.findViewById<TextView>(R.id.subject_name_caption).text = arguments?.getString("LAST_PICKED_SUBJECT_NAME")
    }
}
package com.example.teacherhelper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherhelper.R
import com.example.teacherhelper.model.entities.Subject
import com.example.teacherhelper.viewmodel.adapters.SubjectListAdapter
import com.example.teacherhelper.viewmodel.vms.SubjectsViewModel

class FragmentSubjects: Fragment() {

    private lateinit var viewModel: SubjectsViewModel
    private lateinit var subjectListAdapter: SubjectListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[SubjectsViewModel::class.java]
        subjectListAdapter = SubjectListAdapter(viewModel.subjects)
        return inflater.inflate(R.layout.fragment_subjects, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.subjects.observe(viewLifecycleOwner, {subjectListAdapter.notifyItemRangeChanged(0, subjectListAdapter.itemCount)})

        view.findViewById<Button>(R.id.new_subject_button).setOnClickListener{
            val name = view.findViewById<EditText>(R.id.subject_name_editText).text.toString()
            val group = view.findViewById<EditText>(R.id.subject_group_editText).text.toString()
            val day = view.findViewById<Spinner>(R.id.week_days_spinner).selectedItem.toString()
            val hours = "${view.findViewById<Spinner>(R.id.hours_spinner_1).selectedItem} " +
                    "${view.findViewById<Spinner>(R.id.minutes_spinner_1).selectedItem} : " +
                    "${view.findViewById<Spinner>(R.id.hours_spinner_2).selectedItem} " +
                    "${view.findViewById<Spinner>(R.id.minutes_spinner_2).selectedItem}"
            if(name.isNotBlank() && group.isNotBlank()) {
                viewModel.addSubject(Subject(0, name, group, day, hours))
                viewModel.subjects.observe(viewLifecycleOwner,  {subjectListAdapter.notifyItemInserted(subjectListAdapter.itemCount)})
            }
        }

        view.findViewById<RecyclerView>(R.id.subjects_list).adapter = subjectListAdapter

        view.findViewById<Button>(R.id.edit_subject_button).setOnClickListener{
            if(subjectListAdapter.getSelectedPosition() == -1) {
                val elementNotPickedAlert = "Nie wybrano przedmiotu"
                Toast.makeText(context, elementNotPickedAlert, Toast.LENGTH_SHORT).show()
            }
            else
            it.findNavController().navigate(R.id.action_fragmentSubjects_to_fragmentSubjectEdit, bundleOf(
                "LAST_PICKED_SUBJECT_ID" to subjectListAdapter.getSelectedSubjectId(),
                "LAST_PICKED_SUBJECT_NAME" to subjectListAdapter.getSelectedSubjectName()))
        }
    }
}
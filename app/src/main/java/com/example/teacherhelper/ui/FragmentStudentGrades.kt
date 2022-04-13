package com.example.teacherhelper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherhelper.R
import com.example.teacherhelper.model.entities.Grade
import com.example.teacherhelper.model.utils.Utilities
import com.example.teacherhelper.viewmodel.adapters.GradesListAdapter
import com.example.teacherhelper.viewmodel.adapters.SubjectListAdapter
import com.example.teacherhelper.viewmodel.vms.StudentGradesViewModel

class FragmentStudentGrades: Fragment() {

    private lateinit var viewModel: StudentGradesViewModel
    private lateinit var gradeListAdapter: GradesListAdapter
    private lateinit var studentSubjectsListAdapter: SubjectListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[StudentGradesViewModel::class.java]
        viewModel.setAlbumNumber(arguments?.getString("LAST_PICKED_ALBUM_NUMBER")!!)
        gradeListAdapter = GradesListAdapter(viewModel.studentGrades, viewModel.subjects, viewModel.studentSubjectRelations)
        studentSubjectsListAdapter = SubjectListAdapter(viewModel.subjects)
        return inflater.inflate(R.layout.fragment_student_grades, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.studentGrades.observe(viewLifecycleOwner, {gradeListAdapter.notifyDataSetChanged()})
        viewModel.subjects.observe(viewLifecycleOwner, {studentSubjectsListAdapter.notifyItemRangeChanged(0, studentSubjectsListAdapter.itemCount)})
        viewModel.studentSubjectRelations.observe(viewLifecycleOwner, {  })

        view.findViewById<Button>(R.id.add_grade_button).setOnClickListener {
            if(studentSubjectsListAdapter.getSelectedPosition() == -1) {
                val elementNotPickedAlert = "Subject not picked"
                Toast.makeText(context, elementNotPickedAlert, Toast.LENGTH_SHORT).show()
            }
            else {
                val relationId = Utilities.getStudentSubjectRelationId(arguments?.getString("LAST_PICKED_ALBUM_NUMBER")!!,
                    studentSubjectsListAdapter.getSelectedSubjectId()!!, viewModel.studentSubjectRelations)
                val grade = view.findViewById<Spinner>(R.id.pick_grade_spinner).selectedItem.toString()
                viewModel.addGrade(Grade(0, relationId, grade.toDouble()))
                viewModel.studentGrades.observe(viewLifecycleOwner, {gradeListAdapter.notifyItemInserted(gradeListAdapter.itemCount)})
            }
        }

        view.findViewById<Button>(R.id.check_grades_mean_button).setOnClickListener {
            if(!Utilities.calculateGradesMean(viewModel.studentGrades).isNaN())
            {
                val meanAlert = "The mean is: ${Utilities.calculateGradesMean(viewModel.studentGrades)}"
                Toast.makeText(context, meanAlert, Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<RecyclerView>(R.id.studentGrades_list).adapter = gradeListAdapter
        view.findViewById<RecyclerView>(R.id.pick_subject_recycler).let {
            it.adapter = studentSubjectsListAdapter
            it.itemAnimator = null
        }

        view.findViewById<TextView>(R.id.student_name_caption).text = arguments?.getString("LAST_PICKED_STUDENT_NAME")!!

        //Handling back press example
        //requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
        //   override fun handleOnBackPressed() {
        //        view.findNavController().navigate(R.id.action_fragmentStudentGrades_to_fragmentStudents)
        //    }
        //})
    }
}
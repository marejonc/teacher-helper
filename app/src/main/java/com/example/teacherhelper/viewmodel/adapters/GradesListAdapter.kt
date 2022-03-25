package com.example.teacherhelper.viewmodel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherhelper.R
import com.example.teacherhelper.model.entities.Grade
import com.example.teacherhelper.model.entities.StudentSubjectRelation
import com.example.teacherhelper.model.entities.Subject

class GradesListAdapter(private val studentGrades: LiveData<List<Grade>>, private val subjects: LiveData<List<Subject>>,
    private val studentSubjectRelations: LiveData<List<StudentSubjectRelation>>)
    :RecyclerView.Adapter<GradesListAdapter.GradesListHolder>() {

        inner class GradesListHolder(view: View): RecyclerView.ViewHolder(view) {
            val textViewSubject = view.findViewById<TextView>(R.id.gradeRecord_subject_textView)
            val textViewGrade = view.findViewById<TextView>(R.id.gradeRecord_grade_textView)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradesListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_grade_record, parent, false)
        return GradesListHolder(view)
    }

    override fun onBindViewHolder(holder: GradesListHolder, position: Int) {

        holder.textViewSubject.text = findSubjectNameByRelationId(studentGrades.value?.get(position)?.StudentSubjectRelationID!!)
        holder.textViewGrade.text = studentGrades.value?.get(position)?.GradeValue.toString()
    }

    override fun getItemCount() = studentGrades.value?.size?:0

    private fun findSubjectNameByRelationId(relationId: Int): String {

        var subjectId = 0
        var result = ""
        studentSubjectRelations.value?.forEach {
            if (it.Id == relationId)
                subjectId = it.SubjectID
        }
        subjects.value?.forEach {
            if(it.Id == subjectId)
                result = it.Name
        }
        return result
    }
}
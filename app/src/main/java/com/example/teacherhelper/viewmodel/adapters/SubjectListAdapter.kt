package com.example.teacherhelper.viewmodel.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherhelper.R
import com.example.teacherhelper.model.entities.Subject

class SubjectListAdapter(private val subjects: LiveData<List<Subject>>)
    :RecyclerView.Adapter<SubjectListAdapter.SubjectListHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION
    private var lastSelectedPosition: Int = RecyclerView.NO_POSITION

    inner class SubjectListHolder(view: View): RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                selectedPosition = adapterPosition
                if(lastSelectedPosition == -1) lastSelectedPosition = selectedPosition
                else {
                    notifyItemChanged(lastSelectedPosition)
                    lastSelectedPosition = selectedPosition
                }
                notifyItemChanged(selectedPosition)
            }
        }

        val textViewId = view.findViewById<TextView>(R.id.subjectId_textView)
        val textViewName = view.findViewById<TextView>(R.id.subjectName_textView)
        val textViewGroup = view.findViewById<TextView>(R.id.subjectGroup_textView)
        val textViewWhen = view.findViewById<TextView>(R.id.subjectWhen_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_subject_record, parent, false)
        return SubjectListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SubjectListHolder, position: Int) {

        if(position == selectedPosition)
            holder.itemView.findViewById<LinearLayout>(R.id.single_subject).setBackgroundColor(Color.parseColor("#E6E6E6"))
        else
            holder.itemView.findViewById<LinearLayout>(R.id.single_subject).setBackgroundColor(Color.parseColor("#FFFFFF"))

        holder.textViewId.text = subjects.value?.get(position)?.Id.toString()
        holder.textViewName.text = subjects.value?.get(position)?.Name
        holder.textViewGroup.text = subjects.value?.get(position)?.Group
        holder.textViewWhen.text = subjects.value?.get(position)?.WeekDay.toString().substring(0, 2) +
                ", ${subjects.value?.get(position)?.Hours}"
    }

    override fun getItemCount() = subjects.value?.size?:0

    fun getSelectedPosition() = selectedPosition

    fun getSelectedSubjectId() = subjects.value?.get(selectedPosition)?.Id

    fun getSelectedSubjectName() = "${subjects.value?.get(selectedPosition)?.Name} gr. " +
            subjects.value?.get(selectedPosition)?.Group

    fun resetSelectedPosition() {
        selectedPosition = RecyclerView.NO_POSITION
        lastSelectedPosition = RecyclerView.NO_POSITION
    }
}
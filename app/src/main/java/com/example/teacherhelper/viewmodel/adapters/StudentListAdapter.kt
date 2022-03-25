package com.example.teacherhelper.viewmodel.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherhelper.R
import com.example.teacherhelper.model.entities.Student

class StudentListAdapter(private val students: LiveData<List<Student>>)
    :RecyclerView.Adapter<StudentListAdapter.StudentListHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION
    private var lastSelectedPosition: Int = RecyclerView.NO_POSITION

    inner class StudentListHolder(view: View): RecyclerView.ViewHolder(view) {

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

        val textViewAlbumNumber = view.findViewById<TextView>(R.id.albumNumber_textView)
        val textViewFirstName = view.findViewById<TextView>(R.id.firstName_textView)
        val textViewLastName = view.findViewById<TextView>(R.id.lastName_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_student_record, parent, false)
        return StudentListHolder(view)
    }

    override fun onBindViewHolder(holder: StudentListHolder, position: Int) {

        if(position == selectedPosition)
            holder.itemView.findViewById<LinearLayout>(R.id.single_student).setBackgroundColor(Color.parseColor("#E6E6E6"))
        else
            holder.itemView.findViewById<LinearLayout>(R.id.single_student).setBackgroundColor(Color.parseColor("#FFFFFF"))

        holder.textViewAlbumNumber.text = students.value?.get(position)?.AlbumNumber
        holder.textViewFirstName.text = students.value?.get(position)?.FirstName
        holder.textViewLastName.text = students.value?.get(position)?.LastName
    }

    override fun getItemCount() = students.value?.size?:0

    fun getSelectedPosition() = selectedPosition

    fun getSelectedAlbumNumber() = students.value?.get(selectedPosition)?.AlbumNumber

    fun getSelectedStudentName() = "${students.value?.get(selectedPosition)?.FirstName} " +
            students.value?.get(selectedPosition)?.LastName

    fun resetSelectedPosition() {
        selectedPosition = RecyclerView.NO_POSITION
        lastSelectedPosition = RecyclerView.NO_POSITION
    }
}
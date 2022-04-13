package com.example.teacherhelper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.teacherhelper.R
import com.example.teacherhelper.viewmodel.vms.HomeViewModel

class FragmentHome: Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        return inflater.inflate(R.layout.fragment_home,container,false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.reset_database_button).setOnClickListener {
            val deleteAlert = "Local database reset"
            Toast.makeText(context, deleteAlert, Toast.LENGTH_SHORT).show()

            viewModel.deleteAllData()
        }

        view.findViewById<Button>(R.id.subjects_panel_button).setOnClickListener {
            it.findNavController().navigate(R.id.action_fragmentHome_to_fragmentSubjects)
        }

        view.findViewById<Button>(R.id.students_panel_button).setOnClickListener {
            it.findNavController().navigate(R.id.action_fragmentHome_to_fragmentStudents)
        }
    }
}
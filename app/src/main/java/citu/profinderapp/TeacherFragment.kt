package citu.profinderapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import citu.profinderapp.Firebase.User.FirestoreClient
import citu.profinderapp.Firebase.User.TeacherUser
import citu.profinderapp.databinding.BrowseTeacherFragmentBinding
import citu.profinderapp.databinding.FragmentBrowseBinding

class TeacherFragment : Fragment() {
    private lateinit var teacherAdapter: TeacherAdapter
    private lateinit var teacherList: MutableList<TeacherUser>
    private lateinit var recyclerView: RecyclerView
    private lateinit var firestoreClient: FirestoreClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBrowseBinding.inflate(inflater, container, false)
        firestoreClient = FirestoreClient()

        val recyclerView = binding.recyclerView
        if (recyclerView != null) recyclerView.layoutManager = LinearLayoutManager(requireContext())

        teacherList = mutableListOf()
        teacherAdapter = TeacherAdapter(teacherList)
        if (recyclerView != null) recyclerView.adapter = teacherAdapter

        teacherList = firestoreClient.fetchTeachers(teacherList);

        return binding.root
    }
}
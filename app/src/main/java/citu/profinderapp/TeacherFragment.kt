package citu.profinderapp

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import citu.profinderapp.Firebase.User.FirestoreClient
import citu.profinderapp.Firebase.User.TeacherUser
import citu.profinderapp.databinding.FragmentBrowseBinding


class TeacherFragment : Fragment() {
    private lateinit var teacherAdapter: TeacherAdapter
    private lateinit var teacherList: MutableList<TeacherUser>
    private lateinit var recyclerView: RecyclerView
    private lateinit var firestoreClient: FirestoreClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TeacherFragment", "Fragment Created!") // Add this line
    }


//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentBrowseBinding.inflate(inflater, container, false)
//        return binding.root
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("TeacherFragment", "onCreateView called")
        val binding = FragmentBrowseBinding.inflate(inflater, container, false)
        firestoreClient = FirestoreClient()

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        teacherList = mutableListOf()

        //sample data
//        teacherList = mutableListOf(
//            TeacherUser("dsadasdasda", "dsadasdsa", "dsadasd", "fafasdasd", "teacher", "adfasfas", "dhadhasds"),
//            TeacherUser("bcxbv", "bvbvcb", "dsadasd", "fafasdasd", "teacher", "adfasfas", "dhadhasds"),
//        )

        teacherAdapter = TeacherAdapter(teacherList)

        Log.e("item count", teacherAdapter.itemCount.toString())

        recyclerView.adapter = teacherAdapter

        fetchTeachers()

        Log.e("TeacherFragment", "Returning view for fragment")

        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        recyclerView = binding.recyclerView
//        recyclerView.setLayoutManager(LinearLayoutManager(context))
//        if (teacherList == null || teacherList.isEmpty()) {
//            Log.e("RecyclerViewDebug", "Dataset is empty, RecyclerView won't render items!")
//        } else {
//            Log.e("RecyclerViewDebug", "Dataset size: " + teacherList.size)
//        }
//        val adapter = TeacherAdapter(teacherList)
//        recyclerView.setAdapter(adapter)
//        Log.e("RecyclerViewDebug", "Adapter has been set.")
//    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchTeachers() {
        firestoreClient.fetchTeachers { teachers ->
            teacherList.clear()
            teacherList.addAll(teachers)
            teacherAdapter.notifyDataSetChanged()
            Log.e("TeacherFragment", "Fetched teachers: ${teacherList.size} teachers found.")
            teacherList.forEach { teacher ->
                Log.e("TeacherFragment", teacher.username)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("TeacherFragment", "Fragment is visible")
    }
}
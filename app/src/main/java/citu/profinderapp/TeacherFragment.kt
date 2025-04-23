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
    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TeacherFragment", "Fragment Created!")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("TeacherFragment", "onCreateView called")
        _binding = FragmentBrowseBinding.inflate(inflater, container, false)
        firestoreClient = FirestoreClient()

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        teacherList = mutableListOf()

        //sample data
//        teacherList = mutableListOf(
//            TeacherUser("dsadasdasda", "dsadasdsa", "dsadasd", "fafasdasd", "teacher", "adfasfas", "dhadhasds"),
//            TeacherUser("bcxbv", "bvbvcb", "dsadasd", "fafasdasd", "teacher", "adfasfas", "dhadhasds"),
//        )

        teacherAdapter = TeacherAdapter(requireContext(), teacherList)

        Log.e("item count", teacherAdapter.itemCount.toString())

        recyclerView.adapter = teacherAdapter

        binding.swipeRefresh.setOnRefreshListener {
            fetchTeachers()
        }

        fetchTeachers()

        Log.e("TeacherFragment", "Returning view for fragment")

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchTeachers() {
        binding.swipeRefresh.isRefreshing = true
        firestoreClient.fetchTeachers { teachers ->
            teacherList.clear()
            teacherList.addAll(teachers)
            teacherAdapter.notifyDataSetChanged()
            Log.e("TeacherFragment", "Fetched ${teacherList.size} teachers.")
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("TeacherFragment", "Fragment is visible")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
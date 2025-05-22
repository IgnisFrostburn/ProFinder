package citu.profinderapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import citu.profinderapp.Adapter.TeacherAdapter
import citu.profinderapp.Firebase.User.FirestoreClient
import citu.profinderapp.Firebase.User.TeacherUser
import citu.profinderapp.databinding.FragmentBrowseBinding


class TeacherFragment : Fragment() {
    private lateinit var teacherAdapter: TeacherAdapter
    private lateinit var teacherList: MutableList<TeacherUser>
    private lateinit var recyclerView: RecyclerView
    private lateinit var firestoreClient: FirestoreClient
    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding has been accessed after onDestroyView.")
    private lateinit var allTeachers: MutableList<TeacherUser>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TeacherFragment", "Fragment Created!")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
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

        firestoreClient.fetchTeachers { teachers ->
            allTeachers = teachers.toMutableList()
            teacherList.clear()
            teacherList.addAll(allTeachers)
            teacherAdapter.notifyDataSetChanged()
            Log.e("TeacherFragment", "Fetched ${teacherList.size} teachers.")
            binding.swipeRefresh.isRefreshing = false
        }

        teacherAdapter = TeacherAdapter(requireContext(), teacherList)

        Log.e("item count", teacherAdapter.itemCount.toString())

        recyclerView.adapter = teacherAdapter

        binding.swipeRefresh.setOnRefreshListener {
            fetchTeachers()
        }

        fetchTeachers()

        binding.searchTeacherEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                filterTeachers(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        Log.e("TeacherFragment", "Returning view for fragment")

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterTeachers(query: String) {
        val filteredList = if (query.isEmpty()) {
            allTeachers
        } else {
            allTeachers.filter {
                it.username.contains(query, ignoreCase = true)
            }.toMutableList()
        }

        teacherAdapter.updateList(filteredList)
    }



    @SuppressLint("NotifyDataSetChanged")
    private fun fetchTeachers() {
        binding.swipeRefresh.isRefreshing = true
        firestoreClient.fetchTeachers { teachers ->
            if (isAdded) {
                allTeachers = teachers.toMutableList()
                teacherList.clear()
                teacherList.addAll(allTeachers)
                teacherAdapter.notifyDataSetChanged()
                Log.e("TeacherFragment", "Fetched ${teacherList.size} teachers.")
                binding.swipeRefresh.isRefreshing = false
            } else {
                Log.e("TeacherFragment", "Fragment not added, skipping UI update.")
            }
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
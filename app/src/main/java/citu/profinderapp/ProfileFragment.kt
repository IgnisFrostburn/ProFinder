package citu.profinderapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.mobdev.LoggedInAccount

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(LoggedInAccount.isStudent) {
            Log.e("isStudent", "SUCCESS!")
            return inflater.inflate(R.layout.fragment_profile_student, container, false)
        }
        Log.e("isTeacher", "SUCCESS!")
        return inflater.inflate(R.layout.fragment_profile_teacher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //if teacher
        if(!LoggedInAccount.isStudent) {
            val bioBtn = view.findViewById<Button>(R.id.view_bio_btn)
            val changeBioBtn = view.findViewById<Button>(R.id.change_bio_btn)
            val updateLocationBtn = view.findViewById<Button>(R.id.update_location_btn)

            bioBtn.setOnClickListener {
                val intent = Intent(requireContext(), TeacherBioActivity::class.java)
                startActivity(intent)
            }

            changeBioBtn.setOnClickListener {
                val intent = Intent(requireContext(), ChangeTeacherBioActivity::class.java)
                startActivity(intent)
            }

            updateLocationBtn.setOnClickListener {
                val intent = Intent(requireContext(), UpdateLocationActivity::class.java)
                startActivity(intent)
            }
        } else {
            val changeBioBtn = view.findViewById<Button>(R.id.change_bio_btn)

            changeBioBtn.setOnClickListener {
                val intent = Intent(requireContext(), ChangeStudentBioActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
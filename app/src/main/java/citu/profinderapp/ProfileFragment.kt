package citu.profinderapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import citu.profinderapp.Accounts.LoggedInStudent
import citu.profinderapp.Accounts.LoggedInTeacher
import citu.profinderapp.Firebase.Location.changeDateFormat
import com.bumptech.glide.Glide
import citu.profinderapp.Accounts.LoggedInAccount

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
        val profileImg = view.findViewById<ImageView>(R.id.profile_image_placeholder)
        val profileURL = Uri.parse(LoggedInAccount.profileImg)
        val changeBioBtn = view.findViewById<Button>(R.id.change_bio_btn)
        val username = view.findViewById<TextView>(R.id.username_lbl)
        val email = view.findViewById<TextView>(R.id.email_lbl)
        username.text = LoggedInAccount.username
        email.text = LoggedInAccount.email

        //if teacher
        if(!LoggedInAccount.isStudent) {
            val bioBtn = view.findViewById<Button>(R.id.view_bio_btn)
            val updateLocationBtn = view.findViewById<Button>(R.id.update_location_btn)
            val currentLocation = view.findViewById<TextView>(R.id.current_location_lbl)
            val time = view.findViewById<TextView>(R.id.time_lbl)


            currentLocation.text = LoggedInTeacher.latestLocation?.location ?: "No location"
            time.text = LoggedInTeacher.latestLocation?.time?.let { changeDateFormat(it) } ?: "N/A"
            Glide.with(this)
                .load(profileURL)
                .circleCrop()
                .placeholder(R.drawable.profile_placeholder_icon)
                .error(R.drawable.profile_placeholder_icon)
                .into(profileImg)

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
            val courseYear = view.findViewById<TextView>(R.id.course_and_year_lbl)
            val phoneNumber = view.findViewById<TextView>(R.id.phone_number_lbl)
            val customInfo = view.findViewById<TextView>(R.id.custom_personal_info_lbl)

            courseYear.text = LoggedInStudent.course
            phoneNumber.text = LoggedInStudent.phoneNumber
            customInfo.text = LoggedInStudent.customInfo

            Glide.with(this)
                .load(profileURL)
                .circleCrop()
                .placeholder(R.drawable.profile_placeholder_icon)
                .error(R.drawable.profile_placeholder_icon)
                .into(profileImg)

            changeBioBtn.setOnClickListener {
                val intent = Intent(requireContext(), ChangeStudentBioActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //if teacher ang account mo update and location and time, otherwise only ang unique student fields mo update
        if(!LoggedInAccount.isStudent) {
            updateLocationUI()
        } else {
            requireView().findViewById<TextView>(R.id.course_and_year_lbl).text = LoggedInStudent.course
            requireView().findViewById<TextView>(R.id.phone_number_lbl).text = LoggedInStudent.phoneNumber
            requireView().findViewById<TextView>(R.id.custom_personal_info_lbl).text = LoggedInStudent.customInfo
        }

        //common fields sa both account types
        requireView().findViewById<TextView>(R.id.username_lbl).text = LoggedInAccount.username
        requireView().findViewById<TextView>(R.id.email_lbl).text = LoggedInAccount.email
        val imageView = view?.findViewById<ImageView>(R.id.profile_image_placeholder)
        imageView?.let {
            Glide.with(requireContext())
                .load(LoggedInAccount.profileImg)
                .circleCrop()
                .placeholder(R.drawable.profile_placeholder_icon)
                .into(it)
        }
    }

    private fun updateLocationUI() {
        LoggedInTeacher.latestLocation?.let { location ->
            view?.findViewById<TextView>(R.id.current_location_lbl)!!.text = location.location
            view?.findViewById<TextView>(R.id.time_lbl)!!.text = changeDateFormat(location.time)
        } ?: run {
            view?.findViewById<TextView>(R.id.current_location_lbl)!!.text = "No location"
            view?.findViewById<TextView>(R.id.time_lbl)!!.text = "N/A"
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
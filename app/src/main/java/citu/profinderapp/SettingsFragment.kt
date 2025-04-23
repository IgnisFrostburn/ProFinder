package citu.profinderapp

import BottomSheetFragment
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import citu.profinderapp.Accounts.LoggedInAccount
import citu.profinderapp.Accounts.LoggedInStudent
import citu.profinderapp.Accounts.LoggedInTeacher

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutBtn = view.findViewById<Button>(R.id.logout_btn)
        val devBtn = view.findViewById<Button>(R.id.developer_btn)
        val vicMapBtn = view.findViewById<Button>(R.id.vicinity_map_btn)
        val buildLocBtn = view.findViewById<Button>(R.id.building_locator_btn)

        val dialog = Dialog(requireContext())

        logoutBtn.setOnClickListener {
            dialog.setContentView(R.layout.logout_dialog_box)
            dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.custom_dialog_box))
            dialog.show()

            val dialogLogOutBtn = dialog.findViewById<Button>(R.id.dialog_logout_btn)
            val cancelBtn = dialog.findViewById<Button>(R.id.cancel_btn)

            dialogLogOutBtn.setOnClickListener {
                if(LoggedInAccount.isStudent) clearLoggedInStudent()
                else clearLoggedInTeacher()
                val intent = Intent(requireContext(), LoginPageActivity::class.java)
                startActivity(intent)
            }

            cancelBtn.setOnClickListener {
                dialog.hide()
            }
        }

        devBtn.setOnClickListener {
            BottomSheetFragment().show(parentFragmentManager, BottomSheetFragment().tag)
        }

        vicMapBtn.setOnClickListener {
            dialog.setContentView(R.layout.dialog_vicinity_map)
            dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.custom_dialog_box))
            dialog.show()

            val vicMap = dialog.findViewById<com.github.chrisbanes.photoview.PhotoView>(R.id.vicinity_map)
            vicMap.setZoomable(true)
            vicMap.maximumScale = 10f
            vicMap.setScale(1.0f, true)
        }

        buildLocBtn.setOnClickListener {
            dialog.setContentView(R.layout.dialog_building_locator)
            dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.custom_dialog_box))
            dialog.show()

            val buildLoc = dialog.findViewById<com.github.chrisbanes.photoview.PhotoView>(R.id.building_locator)
            buildLoc.setZoomable(true)
            buildLoc.maximumScale = 10f
            buildLoc.setScale(1.0f, true)
        }
    }

    private fun clearLoggedInAccount() {
        LoggedInAccount.id = ""
        LoggedInAccount.username = null
        LoggedInAccount.email = null
        LoggedInAccount.password = null
        LoggedInAccount.isStudent = false
        LoggedInAccount.profileImg = ""
    }

    private fun clearLoggedInStudent() {
        clearLoggedInAccount()
        LoggedInStudent.course = null
        LoggedInStudent.phoneNumber = null
        LoggedInStudent.customInfo = null
    }

    private fun clearLoggedInTeacher() {
        clearLoggedInAccount()
        LoggedInTeacher.background = null
        LoggedInTeacher.department = null
        LoggedInTeacher.latestLocation = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
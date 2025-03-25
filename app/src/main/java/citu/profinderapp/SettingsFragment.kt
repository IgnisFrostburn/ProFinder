package citu.profinderapp

import BottomSheetFragment
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlin.math.log

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

        val dialog = Dialog(requireContext())

        logoutBtn.setOnClickListener {
            dialog.setContentView(R.layout.logout_dialog_box)
            dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.custom_dialog_box))
            dialog.show()

            val dialogLogOutBtn = dialog.findViewById<Button>(R.id.dialog_logout_btn)
            val cancelBtn = dialog.findViewById<Button>(R.id.cancel_btn)

            dialogLogOutBtn.setOnClickListener {
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
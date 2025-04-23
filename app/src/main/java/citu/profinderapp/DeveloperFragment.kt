

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import citu.profinderapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.developer_bottom_dialog, container, false)

        val closeBtn: ImageView = view.findViewById(R.id.bs_button)
        val instagram: ImageView = view.findViewById(R.id.instagram)
        val facebook: ImageView = view.findViewById(R.id.facebook)
        val youtube: ImageView = view.findViewById(R.id.youtube)


        closeBtn.setOnClickListener {
            dismiss()
        }

        instagram.setOnClickListener {
            val url = "https://www.instagram.com/chuuni_onii_chan/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        facebook.setOnClickListener {
            val url = "https://www.facebook.com/profile.php?id=100018120583719"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        youtube.setOnClickListener {
            val url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        return view
    }
}

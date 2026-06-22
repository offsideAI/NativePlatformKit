package ai.offside.mobile.android.helper.testlabs.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestIconLinkButtonBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TestIconLinkButtonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestIconLinkButtonFragment : Fragment() {

    private lateinit var binding: FragmentTestIconLinkButtonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTestIconLinkButtonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonIconWithLinkLayout.apply {
            btnIconLink01.setOnClickListener {
                Toast.makeText(
                    context,
                    R.string.debug_ui_redesign_button_clicked_label,
                    Toast.LENGTH_SHORT
                ).show()
            }
            btnIconLink02.setOnClickListener {
                Toast.makeText(
                    context,
                    R.string.debug_ui_redesign_button_clicked_label,
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.enabledSwitch.setOnCheckedChangeListener { _, isChecked ->
                btnIconLink01.isEnabled = isChecked
                btnIconLink02.isEnabled = isChecked
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TestIconLinkButtonFragment.
         */
        @JvmStatic
        fun newInstance() = TestIconLinkButtonFragment()
    }
}